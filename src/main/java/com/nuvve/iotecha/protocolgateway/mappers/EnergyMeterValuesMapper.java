package com.nuvve.iotecha.protocolgateway.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.nuvve.iotecha.protocolgateway.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nuvve.iotecha.protocolgateway.domains.EnergyMeterValues;
import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterValuesDto;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EnergyMeterValuesMapper implements CoreMapper<EnergyMeterValuesDto, EnergyMeterValues> {

	@Autowired
	EnergyMeterMapper energyMeterMapper;
	
	@Override
	public EnergyMeterValuesDto transformDomainToDto(EnergyMeterValues domain) throws ProtocolGatewayException {
		EnergyMeterValuesDto dto = EnergyMeterValuesDto.builder()
                .energyMeter(energyMeterMapper.transformDomainToDto(domain.getEnergyMeter()))
                .voltageA(domain.getVoltageA())
                .voltageB(domain.getVoltageB())
                .voltageC(domain.getVoltageC())
                .currentA(domain.getCurrentA())
                .currentB(domain.getCurrentB())
                .currentC(domain.getCurrentC())
                .activePowerA(domain.getActivePowerA())
                .activePowerB(domain.getActivePowerB())
                .activePowerC(domain.getActivePowerC())
                .totalActivePower(domain.getTotalActivePower())
                .reactivePowerA(domain.getReactivePowerA())
                .reactivePowerB(domain.getReactivePowerB())
                .reactivePowerC(domain.getReactivePowerC())
                .energyUp(domain.getEnergyUp())
                .energyDown(domain.getEnergyDown())
                .frequency(domain.getFrequency())
                .powerFactor(domain.getPowerFactor())
                .phaseAngle(domain.getPhaseAngle())
                .timestamp(domain.getTimeStamp())
                .build();
		
        return dto;
	}

	@Override
	public List<EnergyMeterValuesDto> transformDomainToDto(List<EnergyMeterValues> domain)
			throws ProtocolGatewayException {
		List<EnergyMeterValuesDto> dtoList;
		
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
	public EnergyMeterValues transformDtoToDomain(EnergyMeterValuesDto dto) throws ProtocolGatewayException {
		EnergyMeterValues domain = new EnergyMeterValues();
		
		try {
			domain.setVoltageA(dto.getVoltageA());
			domain.setVoltageB(dto.getVoltageB());
			domain.setVoltageC(dto.getVoltageC());
			domain.setCurrentA(dto.getCurrentA());
			domain.setCurrentB(dto.getCurrentB());
			domain.setCurrentC(dto.getCurrentC());
			domain.setActivePowerA(dto.getActivePowerA());
			domain.setActivePowerB(dto.getActivePowerB());
			domain.setActivePowerC(dto.getActivePowerC());
			domain.setTotalActivePower(dto.getTotalActivePower());
			domain.setReactivePowerA(dto.getReactivePowerA());
			domain.setReactivePowerB(dto.getReactivePowerB());
			domain.setReactivePowerC(dto.getReactivePowerC());
			domain.setEnergyUp(dto.getEnergyUp());
			domain.setEnergyDown(dto.getEnergyDown());
			domain.setFrequency(dto.getFrequency());
			domain.setPowerFactor(dto.getPowerFactor());
			domain.setPhaseAngle(dto.getPhaseAngle());
			domain.setTimeStamp(DateUtils.epochMilliToLocaDateTime(Long.parseLong(dto.getMeterTimestamp())));
		}catch(Exception e) {
			 throw new ProtocolGatewayException(e);
		}
		
        return domain;
	}

	@Override
	public List<EnergyMeterValues> transformDtoToDomain(List<EnergyMeterValuesDto> dto)
			throws ProtocolGatewayException {
		List<EnergyMeterValues> domainList;
		
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
