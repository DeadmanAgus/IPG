package com.nuvve.iotecha.protocolgateway.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nuvve.iotecha.protocolgateway.domains.DispatchValues;
import com.nuvve.iotecha.protocolgateway.dtos.DispatchValuesDto;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DispatchValuesMapper implements CoreMapper<DispatchValuesDto, DispatchValues>{

	@Autowired
	EvseMapper evseMapper;
	
	@Override
	public DispatchValuesDto transformDomainToDto(DispatchValues domain) throws ProtocolGatewayException {
		DispatchValuesDto dto = DispatchValuesDto.builder()
                .evse(evseMapper.transformDomainToDto(domain.getEvse()))
                .transactionId(domain.getTransactionId())
                .authorizationResult(domain.getAuthorizationResult())
                .mode(domain.getMode())
                .setpointId(domain.getSetpointId() ) 
                .setpoint(domain.getSetpoint() ) 
                .validity(domain.getValidity() ) 
                .defaultProfile(domain.getDefaultProfile())
                .timestamp(domain.getTimeStamp())
                .build();
		
        return dto;
	}

	@Override
	public List<DispatchValuesDto> transformDomainToDto(List<DispatchValues> domain) throws ProtocolGatewayException {
		List<DispatchValuesDto> dtoList;
		
		dtoList = domain.stream().map(s -> {
			try {
				return this.transformDomainToDto(s);
			} catch (ProtocolGatewayException e) {
				log.error(e.getMessage());
				return null;
			}
		}).collect(Collectors.toList());
   
    	
    	return dtoList;
	}

	@Override
	public DispatchValues transformDtoToDomain(DispatchValuesDto dto) throws ProtocolGatewayException {
		DispatchValues domain = new DispatchValues();
		
		try {
			domain.setTransactionId(dto.getTransactionId());
			domain.setAuthorizationResult(dto.getAuthorizationResult());
			domain.setMode(dto.getMode());
			domain.setSetpointId(dto.getSetpointId()); 
			domain.setSetpoint(dto.getSetpoint());
			domain.setValidity(dto.getValidity());
			domain.setDefaultProfile(dto.getDefaultProfile());
			domain.setTimeStamp(dto.getTimeStamp());
		}catch(Exception e) {
			 throw new ProtocolGatewayException(e);
		}
		
        return domain;
	}

	@Override
	public List<DispatchValues> transformDtoToDomain(List<DispatchValuesDto> dto) throws ProtocolGatewayException {
		List<DispatchValues> domainList;
		
		domainList = dto.stream().map(s -> {
				try {
					return this.transformDtoToDomain(s);
				} catch (ProtocolGatewayException e) {
					log.error(e.getMessage());
					return null;
				}
			}).collect(Collectors.toList());
   
    	
    	return domainList;
	}

}
