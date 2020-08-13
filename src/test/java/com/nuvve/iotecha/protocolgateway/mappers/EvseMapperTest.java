package com.nuvve.iotecha.protocolgateway.mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nuvve.iotecha.protocolgateway.domains.Evse;
import com.nuvve.iotecha.protocolgateway.dtos.EvseDto;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;

@SpringBootTest
public class EvseMapperTest {

    private static final String MAKER = "maker";
    private static final String MAKER2 = "maker2";
    private static final String SERIAL_NUMBER = "serialNumber";
    private static final String SECC_MAC = "seccMac";
    private static final String POWER_RATINGS = "powerRatings";
    private static final long ID = 0l;
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Autowired
    private EvseMapper mapper;

    private Evse evse;
    private EvseDto evseDto;

    @BeforeEach
    public void setUp() {
        setEvse();
        setEvseDto();
    }

    @Test
    public void transformDomainToDtoTest() throws ProtocolGatewayException {
        EvseDto dto = mapper.transformDomainToDto(evse);

        Assertions.assertEquals(evse.getEvseMake(), dto.getEvseMake());
        Assertions.assertEquals(evse.getEvseSn(), dto.getEvseSn());
        Assertions.assertEquals(evse.getSeccMac(), dto.getSeccMac());
        Assertions.assertEquals(evse.getPowerRatings(), dto.getPowerRatings());
    }

    @Test
    public void transformDomainToDtoListTest() throws ProtocolGatewayException {
        List<Evse> evseList = new ArrayList<>();
        evseList.add(evse);
        evseList.add(setOtherEvse());
        List<EvseDto> dtoList = mapper.transformDomainToDto(evseList);

        EvseDto dto = dtoList.get(0);

        Assertions.assertEquals(evse.getEvseMake(), dto.getEvseMake());
        Assertions.assertEquals(evse.getEvseSn(), dto.getEvseSn());
        Assertions.assertEquals(evse.getSeccMac(), dto.getSeccMac());
        Assertions.assertEquals(evse.getPowerRatings(), dto.getPowerRatings());
        Assertions.assertEquals(MAKER2, dtoList.get(1).getEvseMake());
    }

    @Test
    public void transformDtoToDomainTest() throws ProtocolGatewayException {
        Evse domain = mapper.transformDtoToDomain(evseDto);

        Assertions.assertEquals(evseDto.getEvseMake(), domain.getEvseMake());
        Assertions.assertEquals(evseDto.getEvseSn(), domain.getEvseSn());
        Assertions.assertEquals(evseDto.getSeccMac(), domain.getSeccMac());
        Assertions.assertEquals(evseDto.getPowerRatings(), domain.getPowerRatings());
    }

    @Test
    public void transformDtoToDomainListTest() throws ProtocolGatewayException {
        List<EvseDto> evseDtoList = new ArrayList<>();
        evseDtoList.add(evseDto);
        evseDtoList.add(setOtherEvseDto());
        List<Evse> dtoList = mapper.transformDtoToDomain(evseDtoList);

        Evse dto = dtoList.get(0);

        Assertions.assertEquals(evse.getEvseMake(), dto.getEvseMake());
        Assertions.assertEquals(evse.getEvseSn(), dto.getEvseSn());
        Assertions.assertEquals(evse.getSeccMac(), dto.getSeccMac());
        Assertions.assertEquals(evse.getPowerRatings(), dto.getPowerRatings());
        Assertions.assertEquals(MAKER2, dtoList.get(1).getEvseMake());
    }

    private void setEvseDto(){
        evseDto = EvseDto.builder()
                .evseMake(MAKER)
                .evseSn(SERIAL_NUMBER)
                .seccMac(SECC_MAC)
                .powerRatings(POWER_RATINGS)
                .build();
    }

    private EvseDto setOtherEvseDto(){
        return EvseDto.builder()
                .evseMake(MAKER2)
                .evseSn(SERIAL_NUMBER)
                .seccMac(SECC_MAC)
                .powerRatings(POWER_RATINGS)
                .build();
    }

    private void setEvse(){
        evse = new Evse();
        evse.setId(ID);
        evse.setEvseMake(MAKER);
        evse.setEvseSn(SERIAL_NUMBER);
        evse.setSeccMac(SECC_MAC);
        evse.setPowerRatings(POWER_RATINGS);
        evse.setCreatedDate(NOW);
    }

    private Evse setOtherEvse(){
        Evse evse = new Evse();
        evse.setId(ID);
        evse.setEvseMake(MAKER2);
        evse.setEvseSn(SERIAL_NUMBER);
        evse.setSeccMac(SECC_MAC);
        evse.setPowerRatings(POWER_RATINGS);
        evse.setCreatedDate(NOW);
        return evse;
    }
}
