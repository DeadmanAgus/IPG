package com.nuvve.iotecha.protocolgateway.mappers;

import com.nuvve.iotecha.protocolgateway.domains.Evse;
import com.nuvve.iotecha.protocolgateway.dtos.EvseDto;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EvseMapper implements CoreMapper<EvseDto, Evse> {

    @Override
    public EvseDto transformDomainToDto(Evse domain) throws ProtocolGatewayException {
        EvseDto dto = EvseDto.builder()
                .evseId(domain.getId())
                .evseMake(domain.getEvseMake())
                .evseSn(domain.getEvseSn())
                .seccMac(domain.getSeccMac())
                .powerRatings(domain.getPowerRatings())
                .timestamp(domain.getCreatedDate())
                .build();
        return dto;
    }

    @Override
    public List<EvseDto> transformDomainToDto(List<Evse> domain) throws ProtocolGatewayException {
        List<EvseDto> evseDtoList;
        evseDtoList = domain.stream().map(s -> {
            try {
                return this.transformDomainToDto(s);
            } catch (ProtocolGatewayException e) {
                log.error(e.getMessage());
                return null;
            }
        }).collect(Collectors.toList());
        return evseDtoList;
    }

    @Override
    public Evse transformDtoToDomain(EvseDto dto) throws ProtocolGatewayException {
        Evse domain = new Evse();
        domain.setEvseMake(dto.getEvseMake());
        domain.setEvseSn(dto.getEvseSn());
        domain.setSeccMac(dto.getSeccMac());
        domain.setPowerRatings(dto.getPowerRatings());
        return domain;
    }

    @Override
    public List<Evse> transformDtoToDomain(List<EvseDto> dto) throws ProtocolGatewayException {
        List<Evse> evseList;
        evseList = dto.stream().map(s -> {
            try {
                return this.transformDtoToDomain(s);
            } catch (ProtocolGatewayException e) {
                log.error(e.getMessage());
                return null;
            }
        }).collect(Collectors.toList());
        return evseList;
    }

}
