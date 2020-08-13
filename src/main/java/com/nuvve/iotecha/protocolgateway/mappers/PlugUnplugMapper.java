package com.nuvve.iotecha.protocolgateway.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nuvve.iotecha.protocolgateway.domains.PlugUnplug;
import com.nuvve.iotecha.protocolgateway.dtos.PlugUnplugDto;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PlugUnplugMapper implements CoreMapper<PlugUnplugDto, PlugUnplug>{
	
	@Autowired
	EvseMapper evseMapper;

	@Override
	public PlugUnplugDto transformDomainToDto(PlugUnplug domain) throws ProtocolGatewayException {
		PlugUnplugDto dto = PlugUnplugDto.builder()
				.evse(evseMapper.transformDomainToDto(domain.getEvse()))
                .transactionId(domain.getTransactionId())
                .connectorId(domain.getConnectorId())
                .evccMac(domain.getEvccMac())
                .eventType(domain.getEventType())
                .timestamp(domain.getTimeStamp())
                .build();
		
        return dto;
	}

	@Override
	public List<PlugUnplugDto> transformDomainToDto(List<PlugUnplug> domain) throws ProtocolGatewayException {
		List<PlugUnplugDto> plugUnplugDtoList;
		
		plugUnplugDtoList = domain.stream().map(s -> {
				try {
					return this.transformDomainToDto(s);
				} catch (ProtocolGatewayException e) {
					log.error(e.getMessage());
					return null;
				}
			}).collect(Collectors.toList());
   
    	
    	return plugUnplugDtoList;
	}

	@Override
	public PlugUnplug transformDtoToDomain(PlugUnplugDto dto) throws ProtocolGatewayException {
		PlugUnplug domain = new PlugUnplug();
		
        domain.setTransactionId(dto.getTransactionId());
        domain.setConnectorId(dto.getConnectorId());
        domain.setEvccMac(dto.getEvccMac());
        domain.setEventType(dto.getEventType());
        domain.setTimeStamp(dto.getTimestamp());
        
        return domain;
	}

	@Override
	public List<PlugUnplug> transformDtoToDomain(List<PlugUnplugDto> dto) throws ProtocolGatewayException {
		List<PlugUnplug> plugUnplugList;
		
		plugUnplugList = dto.stream().map(s -> {
				try {
					return this.transformDtoToDomain(s);
				} catch (ProtocolGatewayException e) {
					log.error(e.getMessage());
					return null;
				}
			}).collect(Collectors.toList());
   
    	
    	return plugUnplugList;
	}

}
