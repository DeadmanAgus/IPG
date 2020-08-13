package com.nuvve.iotecha.protocolgateway.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuvve.iotecha.protocolgateway.domains.EnergyMeter;
import com.nuvve.iotecha.protocolgateway.domains.EnergyMeterValues;
import com.nuvve.iotecha.protocolgateway.domains.Evse;
import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterDto;
import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterValuesDto;
import com.nuvve.iotecha.protocolgateway.dtos.EventType;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;
import com.nuvve.iotecha.protocolgateway.mappers.EnergyMeterMapper;
import com.nuvve.iotecha.protocolgateway.mappers.EnergyMeterValuesMapper;
import com.nuvve.iotecha.protocolgateway.persistence.EnergyMeterRepository;
import com.nuvve.iotecha.protocolgateway.persistence.EnergyMeterValuesRepository;
import com.nuvve.iotecha.protocolgateway.persistence.EvseRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EnergyMeterService {
    private final String ENERGY_METER_DELETED_CORRECTLY = "energyMeter deleted correctly";
    private final String EVSE_NOT_FOUND = "EVSE not found";
    private final String ENERGY_METER_SAVED_CORRECTLY = "Energy Meter Saved Correctly";
    private final String ENERGY_METER_NOT_FOUND = "EnergyMeter not found";
    private final String ENERGY_METER_UPDATED_CORRECTLY = "energyMeter updated correctly";
    private final String EVSE_ID_MUST_BE_UNIQUE = "EVSE id already exist and must be unique";

    @Autowired
	EvseRepository evseRepository;
	
    @Autowired
    EnergyMeterRepository energyMeterRepository;
    
    @Autowired
    EnergyMeterValuesRepository energyMeterValuesRepository;

    @Autowired
    EnergyMeterMapper mapper;
    
    @Autowired
    EnergyMeterValuesMapper energyMeterValuesMapper;
    
    /**
     * Creates an energy meter
     * 
     * @param energyMeter
     * @return
     * @throws ProtocolGatewayException
     */
    public EnergyMeterDto createEnergyMeter(EnergyMeterDto energyMeter)throws ProtocolGatewayException {
    	Optional<Evse> evse;
        EnergyMeter domain = mapper.transformDtoToDomain(energyMeter);
        EnergyMeterDto response = null;
        evse = evseRepository.findById(energyMeter.getEvse().getEvseId());
        if (!evse.isPresent()) {
			throw new ProtocolGatewayException(EVSE_NOT_FOUND);
		}
        Optional<EnergyMeter> evseId = energyMeterRepository.findByEvseId(energyMeter.getEvse().getEvseId());
        if (evseId.isPresent()) {
            throw new ProtocolGatewayException(EVSE_ID_MUST_BE_UNIQUE);
        }
        try {
	        domain.setEvse(evse.get());
            response = mapper.transformDomainToDto(energyMeterRepository.save(domain));
            log.info(ENERGY_METER_SAVED_CORRECTLY);
        } catch (Exception e) {
			throw new ProtocolGatewayException(e);
        }
        response.setEventType(EventType.RESPONSE.name());
        response.setDescription(ENERGY_METER_SAVED_CORRECTLY);
        return response;
    }

    /**
     * Update an EVSE
     *
     * @param energyMeter
     * @return EnergyMeterDto
     * @throws ProtocolGatewayException
     */
    public EnergyMeterDto updateEnergyMeter(EnergyMeterDto energyMeter) throws ProtocolGatewayException {
        EnergyMeterDto response = null;
        Optional<Evse> evse;
        try {
            Optional<EnergyMeter> energyMeterUpdated = energyMeterRepository.findById(energyMeter.getEnergyMeterId());
            if (energyMeterUpdated.isEmpty()) {
                throw new ProtocolGatewayException(ENERGY_METER_NOT_FOUND);
            }
            evse = evseRepository.findById(energyMeter.getEvse().getEvseId());
    		if (!evse.isPresent()) {
    			throw new ProtocolGatewayException(EVSE_NOT_FOUND);
    		}
            Optional<EnergyMeter> evseId = energyMeterRepository.findByEvseId(energyMeter.getEvse().getEvseId());
            if (evseId.isPresent()) {
                throw new ProtocolGatewayException(EVSE_ID_MUST_BE_UNIQUE);
            }
            EnergyMeter updated = mapper.transformDtoToDomain(energyMeter);
            updated.setEvse(evse.get());
            updated.setId(energyMeterUpdated.get().getId());
            updated.setCreatedDate(energyMeterUpdated.get().getCreatedDate());
            updated.setEnabled(energyMeterUpdated.get().isEnabled());
            response = mapper.transformDomainToDto(energyMeterRepository.save(updated));
            log.info(ENERGY_METER_UPDATED_CORRECTLY);
        } catch (Exception e) {
            throw new ProtocolGatewayException(e.getMessage());
        }
        response.setEventType(EventType.RESPONSE.name());
        response.setDescription(ENERGY_METER_UPDATED_CORRECTLY);
        return response;
    }

    /**
     * Creates an EVSE
     *
     * @param id
     * @return EnergyMeterDto
     * @throws ProtocolGatewayException
     */
    public EnergyMeterDto deleteEnergyMeterById(long id) throws ProtocolGatewayException {
        try {
            energyMeterRepository.deleteById(id);
            log.info(ENERGY_METER_DELETED_CORRECTLY);
        } catch (Exception e) {
            throw new ProtocolGatewayException(e);
        }
        EnergyMeterDto response = EnergyMeterDto.builder()
                .energyMeterId(id)
                .eventType(EventType.RESPONSE.name())
                .description(ENERGY_METER_DELETED_CORRECTLY)
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
    public EnergyMeterDto findById(long id) throws ProtocolGatewayException {
        Optional<EnergyMeter> energyMeter = energyMeterRepository.findById(id);
        return energyMeter.isPresent() ? mapper.transformDomainToDto(energyMeter.get()) : null;
    }

    /**
     * Retrieves all EVSEs
     *
     * @return
     * @throws ProtocolGatewayException
     */
    public List<EnergyMeterDto> getAll() throws ProtocolGatewayException {
        return mapper.transformDomainToDto(energyMeterRepository.findAll());
    }
    
    /**
     * Creates an energy meter Info
     * 
     * @param energyMeterValues
     * @return
     * @throws ProtocolGatewayException
     */
    public EnergyMeterValuesDto createEnergyMeterInfo(EnergyMeterValuesDto energyMeterValues)throws ProtocolGatewayException {
    	Optional<EnergyMeter> energyMeter;
        EnergyMeterValues domain = energyMeterValuesMapper.transformDtoToDomain(energyMeterValues);
        EnergyMeterValuesDto response = null;
        energyMeter = energyMeterRepository.findById(energyMeterValues.getEnergyMeter().getEnergyMeterId());
		if (!energyMeter.isPresent()) {
			throw new ProtocolGatewayException(ENERGY_METER_NOT_FOUND);
		}
        try {
	        domain.setEnergyMeter(energyMeter.get());
        	
            response = energyMeterValuesMapper.transformDomainToDto(energyMeterValuesRepository.save(domain));
            
            log.info(ENERGY_METER_SAVED_CORRECTLY);
        } catch (Exception e) {
			throw new ProtocolGatewayException(e);
        }
        return response;
    }
}
