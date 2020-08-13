package com.nuvve.iotecha.protocolgateway.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.nuvve.iotecha.protocolgateway.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nuvve.iotecha.protocolgateway.domains.EvseValues;
import com.nuvve.iotecha.protocolgateway.dtos.EvseValuesDto;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EvseValuesMapper implements CoreMapper<EvseValuesDto, EvseValues> {

	@Autowired
	EvseMapper evseMapper;
	
	@Override
	public EvseValuesDto transformDomainToDto(EvseValues domain) throws ProtocolGatewayException {
		EvseValuesDto dto = EvseValuesDto.builder()
                .evse(evseMapper.transformDomainToDto(domain.getEvse()))
                .transactionId(domain.getTransactionId())
                .connectorId(domain.getConnectorId())
                .evseNominalAcCapacityUp(domain.getEvseNominalAcCapacityUp())
                .evseNominalAcCapacityDown(domain.getEvseNominalAcCapacityDown())
                .evseActualAcCapacityDown(domain.getEvseActualAcCapacityDown())
                .evseActualAcCapacityUp(domain.getEvseActualAcCapacityUp())
                .evseEfficiency(domain.getEvseEfficiency())
                .evMaxVoltage(domain.getEvMaxVoltage())
                .evMaxCurrent(domain.getEvMaxCurrent())
                .evMaxPower(domain.getEvMaxPower())
                .evTargetVoltage(domain.getEvTargetVoltage())
                .evTargetCurrent(domain.getEvTargetCurrent())
                .evEnergyCapacity(domain.getEvEnergyCapacity())
                .sessionEnergyIn(domain.getSessionEnergyIn())
                .sessionEnergyOut(domain.getSessionEnergyOut())
                .soc(domain.getSoc())
                .temperatures(domain.getTemperatures())
                .evseMode(domain.getEvseMode())
                .timestamp(domain.getTimeStamp())
                .build();
		
        return dto;
	}

	@Override
	public List<EvseValuesDto> transformDomainToDto(List<EvseValues> domain) throws ProtocolGatewayException {
		List<EvseValuesDto> dtoList;
		
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
	public EvseValues transformDtoToDomain(EvseValuesDto dto) throws ProtocolGatewayException {
		EvseValues domain = new EvseValues();
		
		try {
			domain.setTransactionId(dto.getTransactionId());
			domain.setConnectorId(dto.getConnectorId());
			domain.setEvseNominalAcCapacityUp(dto.getEvseNominalAcCapacityUp());
			domain.setEvseNominalAcCapacityDown(dto.getEvseNominalAcCapacityDown());
			domain.setEvseActualAcCapacityDown(dto.getEvseActualAcCapacityDown());
			domain.setEvseActualAcCapacityUp(dto.getEvseActualAcCapacityUp());
			domain.setEvseEfficiency(dto.getEvseEfficiency());
			domain.setEvMaxVoltage(dto.getEvMaxVoltage());
			domain.setEvMaxCurrent(dto.getEvMaxCurrent());
			domain.setEvMaxPower(dto.getEvMaxPower());
			domain.setEvTargetVoltage(dto.getEvTargetVoltage());
			domain.setEvTargetCurrent(dto.getEvTargetCurrent());
			domain.setEvEnergyCapacity(dto.getEvEnergyCapacity());
			domain.setSessionEnergyIn(dto.getSessionEnergyIn());
			domain.setSessionEnergyOut(dto.getSessionEnergyOut());
			domain.setSoc(dto.getSoc());
			domain.setTemperatures(dto.getTemperatures());
			domain.setEvseMode(dto.getEvseMode());
			domain.setTimeStamp(DateUtils.epochMilliToLocaDateTime(Long.parseLong(dto.getEvseTimestamp())));
		}catch(Exception e) {
			 throw new ProtocolGatewayException(e);
		}
		
        return domain;
	}

	@Override
	public List<EvseValues> transformDtoToDomain(List<EvseValuesDto> dto) throws ProtocolGatewayException {
		List<EvseValues> domainList;
		
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
