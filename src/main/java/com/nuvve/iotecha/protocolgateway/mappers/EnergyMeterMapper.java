package com.nuvve.iotecha.protocolgateway.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nuvve.iotecha.protocolgateway.domains.EnergyMeter;
import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterDto;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EnergyMeterMapper implements CoreMapper<EnergyMeterDto, EnergyMeter> {
	
	@Autowired
	EvseMapper evseMapper;
	
	@Override
	public EnergyMeterDto transformDomainToDto(EnergyMeter domain) throws ProtocolGatewayException {
        EnergyMeterDto dto = EnergyMeterDto.builder()
                .evse(evseMapper.transformDomainToDto(domain.getEvse()))
                .meterId(domain.getId())
                .meterMake(domain.getMeterMake())
                .meterModel(domain.getMeterModel())
                .meterSn(domain.getMeterSn())
                .build();
        return dto;
	}

	@Override
	public List<EnergyMeterDto> transformDomainToDto(List<EnergyMeter> domain) throws ProtocolGatewayException {
		List<EnergyMeterDto> energyMeterDto;
		
		energyMeterDto = domain.stream().map(s -> {
				try {
					return this.transformDomainToDto(s);
				} catch (ProtocolGatewayException e) {
					log.error(e.getMessage());
					return null;
				}
			}).collect(Collectors.toList());
   
    	
    	return energyMeterDto;
	}

	@Override
	public EnergyMeter transformDtoToDomain(EnergyMeterDto dto) throws ProtocolGatewayException {
		EnergyMeter domain = new EnergyMeter();
		
		try {
	        domain.setMeterMake(dto.getMeterMake());
	        domain.setMeterModel(dto.getMeterModel());
	        domain.setMeterSn(dto.getMeterSn());
		}catch(Exception e) {
			 throw new ProtocolGatewayException(e);
		}
		
        return domain;
	}

	@Override
	public List<EnergyMeter> transformDtoToDomain(List<EnergyMeterDto> dto) throws ProtocolGatewayException {
		List<EnergyMeter> energyMeterList;
		
		energyMeterList = dto.stream().map(s -> {
				try {
					return this.transformDtoToDomain(s);
				} catch (ProtocolGatewayException e) {
					log.error(e.getMessage());
					return null;
				}
			}).collect(Collectors.toList());
   
    	
    	return energyMeterList;
	}

}
