package com.nuvve.iotecha.protocolgateway.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuvve.iotecha.protocolgateway.domains.Evse;
import com.nuvve.iotecha.protocolgateway.domains.EvseValues;
import com.nuvve.iotecha.protocolgateway.dtos.EventType;
import com.nuvve.iotecha.protocolgateway.dtos.EvseDto;
import com.nuvve.iotecha.protocolgateway.dtos.EvseValuesDto;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;
import com.nuvve.iotecha.protocolgateway.mappers.EvseMapper;
import com.nuvve.iotecha.protocolgateway.mappers.EvseValuesMapper;
import com.nuvve.iotecha.protocolgateway.persistence.EvseRepository;
import com.nuvve.iotecha.protocolgateway.persistence.EvseValuesRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvseService {

    private static final String EVSE_SAVED_CORRECTLY = "evse saved correctly";
    private static final String EVSE_NOT_FOUND = "Evse not found";
    private static final String EVSE_UPDATED_CORRECTLY = "evse updated correctly";
    private static final String EVSE_DELETED_CORRECTLY = "evse deleted correctly";

    @Autowired
    EvseRepository evseRepository;

    @Autowired
    EvseMapper mapper;
    
    @Autowired
    EvseValuesRepository evseValuesRepository;

    @Autowired
    EvseValuesMapper evseValuesmapper;


    /**
     * Creates an EVSE
     *
     * @param evse
     * @return EvseDto
     * @throws ProtocolGatewayException
     */
    public EvseDto createEvse(EvseDto evse) throws ProtocolGatewayException {
        EvseDto response = null;
        try {
            response = mapper.transformDomainToDto(evseRepository.save(mapper.transformDtoToDomain(evse)));
            log.info(EVSE_SAVED_CORRECTLY);
        } catch (Exception e) {
            throw new ProtocolGatewayException(e.getMessage());
        }
        response.setEventType(EventType.RESPONSE.name());
        response.setDescription(EVSE_SAVED_CORRECTLY);
        return response;
    }

    /**
     * Update an EVSE
     *
     * @param evse
     * @return EvseDto
     * @throws ProtocolGatewayException
     */
    public EvseDto updateEvse(EvseDto evse) throws ProtocolGatewayException {
        EvseDto response = null;
        try {
            Optional<Evse> evseUpdated = evseRepository.findById(evse.getEvseId());
            if (evseUpdated.isEmpty()) {
                throw new ProtocolGatewayException(EVSE_NOT_FOUND);
            }
            Evse updated = mapper.transformDtoToDomain(evse);
            updated.setId(evseUpdated.get().getId());
            updated.setCreatedDate(evseUpdated.get().getCreatedDate());
            updated.setEnabled(evseUpdated.get().isEnabled());
            response = mapper.transformDomainToDto(evseRepository.save(updated));
            log.info(EVSE_UPDATED_CORRECTLY);
        } catch (Exception e) {
            throw new ProtocolGatewayException(e.getMessage());
        }
        response.setEventType(EventType.RESPONSE.name());
        response.setDescription(EVSE_UPDATED_CORRECTLY);
        return response;
    }

    /**
     * Creates an EVSE
     *
     * @param id
     * @return EvseDto
     * @throws ProtocolGatewayException
     */
    public EvseDto deleteEvseById(long id) throws ProtocolGatewayException {
        try {
            evseRepository.deleteById(id);
            log.info(EVSE_DELETED_CORRECTLY);
        } catch (Exception e) {
            throw new ProtocolGatewayException(e);
        }
        EvseDto response = EvseDto.builder()
                .evseId(id)
                .eventType(EventType.RESPONSE.name())
                .description(EVSE_DELETED_CORRECTLY)
                .build();
        return response;
    }

    /**
     * Finds an EVSE per Id
     *
     * @param id
     * @return
     * @throws ProtocolGatewayException
     */
    public EvseDto findById(long id) throws ProtocolGatewayException {
        Optional<Evse> evse = evseRepository.findById(id);
        return evse.isPresent() ? mapper.transformDomainToDto(evse.get()) : null;
    }

    /**
     * Retrieves all EVSEs
     *
     * @return
     * @throws ProtocolGatewayException
     */
    public List<EvseDto> getAll() throws ProtocolGatewayException {
        return mapper.transformDomainToDto(evseRepository.findAll());
    }
    
    /**
     * Creates an EVSE Info
     *
     * @param evseValues
     * @return EvseDto
     * @throws ProtocolGatewayException
     */
    public EvseValuesDto createEvseInfo(EvseValuesDto evseValues) throws ProtocolGatewayException {
    	Optional<Evse> evse;
    	EvseValuesDto response = null;
    	EvseValues domain;
    	
    	evse = evseRepository.findById(evseValues.getEvse().getEvseId());

		if (!evse.isPresent()) {
			throw new ProtocolGatewayException(EVSE_NOT_FOUND);
		}
    	
        try {
        	domain = evseValuesmapper.transformDtoToDomain(evseValues);
        	domain.setEvse(evse.get());
        	
            response = evseValuesmapper.transformDomainToDto(evseValuesRepository.save(domain));
            
            log.info(EVSE_SAVED_CORRECTLY);
        } catch (Exception e) {
            throw new ProtocolGatewayException(e.getMessage());
        }
        
        response.setEventType(EventType.RESPONSE.name());
        response.setDescription(EVSE_SAVED_CORRECTLY);
        return response;
    }
}
