package com.nuvve.iotecha.protocolgateway.mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nuvve.iotecha.protocolgateway.domains.EnergyMeter;
import com.nuvve.iotecha.protocolgateway.domains.Evse;
import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterDto;
import com.nuvve.iotecha.protocolgateway.dtos.EvseDto;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;

@SpringBootTest
public class EnergyMeterMapperTest {
    private static final String MAKER = "maker";
    private static final String MAKER2 = "maker2";
    private static final String SERIAL_NUMBER = "serialNumber";
    private static final String MODEL = "model";
    private static final String POWER_RATINGS = "powerRatings";
    private static final String SECC_MAC = "seccMac";
    private static final long ID = 0l;
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Autowired
    private EnergyMeterMapper mapper;

    @Autowired
    private EvseMapper evseMapper;
    
    private EnergyMeter energyMeter;
    private EnergyMeterDto energyMeterDto;

    @BeforeEach
    public void setUp() {
        setEnergyMeter();
        setEnergyMeterDto();
    }

    @Test
    public void transformDomainToDtoTest() throws ProtocolGatewayException {
        EnergyMeterDto dto = mapper.transformDomainToDto(energyMeter);

        Assertions.assertEquals(energyMeter.getMeterMake(), dto.getMeterMake());
        Assertions.assertEquals(energyMeter.getMeterModel(), dto.getMeterModel());
        Assertions.assertEquals(energyMeter.getMeterSn(), dto.getMeterSn());
        Assertions.assertEquals(energyMeter.getEvse().getEvseMake(), dto.getEvse().getEvseMake());
    }

    @Test
    public void transformDomainToDtoListTest() throws ProtocolGatewayException {
        List<EnergyMeter> evseList = new ArrayList<>();
        evseList.add(energyMeter);
        evseList.add(setOtherEnergyMeter());
        List<EnergyMeterDto> dtoList = mapper.transformDomainToDto(evseList);

        EnergyMeterDto dto = dtoList.get(0);

        Assertions.assertEquals(energyMeter.getMeterMake(), dto.getMeterMake());
        Assertions.assertEquals(energyMeter.getMeterModel(), dto.getMeterModel());
        Assertions.assertEquals(energyMeter.getMeterSn(), dto.getMeterSn());
        Assertions.assertEquals(energyMeter.getEvse().getEvseMake(), dto.getEvse().getEvseMake());
        Assertions.assertEquals(MAKER2, dtoList.get(1).getMeterMake());
    }

    @Test
    public void transformDtoToDomainTest() throws ProtocolGatewayException {
        EnergyMeter domain = mapper.transformDtoToDomain(energyMeterDto);
        Evse evse = evseMapper.transformDtoToDomain(energyMeterDto.getEvse());
        domain.setEvse(evse);
        
        Assertions.assertEquals(energyMeterDto.getMeterMake(), domain.getMeterMake());
        Assertions.assertEquals(energyMeterDto.getMeterModel(), domain.getMeterModel());
        Assertions.assertEquals(energyMeterDto.getMeterSn(), domain.getMeterSn());
        Assertions.assertEquals(energyMeter.getEvse().getEvseMake(), domain.getEvse().getEvseMake());
    }

    @Test
    public void transformDtoToDomainListTest() throws ProtocolGatewayException {
        List<EnergyMeterDto> energyMeterDtos = new ArrayList<>();
        energyMeterDtos.add(energyMeterDto);
        energyMeterDtos.add(setOtherEnergyMeterDto());
        List<EnergyMeter> dtoList = mapper.transformDtoToDomain(energyMeterDtos);

        EnergyMeter dto = dtoList.get(0);

        Assertions.assertEquals(energyMeter.getMeterMake(), dto.getMeterMake());
        Assertions.assertEquals(energyMeter.getMeterModel(), dto.getMeterModel());
        Assertions.assertEquals(energyMeter.getMeterSn(), dto.getMeterSn());
        Assertions.assertEquals(MAKER, dtoList.get(1).getMeterMake());
    }

    private void setEnergyMeterDto(){
        energyMeterDto = EnergyMeterDto.builder()
                .evse(setEvseDto())
                .meterMake(MAKER)
                .meterModel(MODEL)
                .meterSn(SERIAL_NUMBER)
                .build();
    }

    private EnergyMeterDto setOtherEnergyMeterDto(){
        return EnergyMeterDto.builder()
                .evse(setEvseDto())
                .meterMake(MAKER)
                .meterModel(MODEL)
                .meterSn(SERIAL_NUMBER)
                .build();
    }

    private void setEnergyMeter(){
        energyMeter = new EnergyMeter();
        energyMeter.setId(ID);
        energyMeter.setEvse(setEvse());
        energyMeter.setMeterMake(MAKER);
        energyMeter.setMeterModel(MODEL);
        energyMeter.setMeterSn(SERIAL_NUMBER);
        energyMeter.setCreatedDate(NOW);
    }

    private EnergyMeter setOtherEnergyMeter(){
        EnergyMeter energyMeter = new EnergyMeter();
        energyMeter.setId(ID);
        energyMeter.setEvse(setEvse());
        energyMeter.setMeterMake(MAKER2);
        energyMeter.setMeterModel(MODEL);
        energyMeter.setMeterSn(SERIAL_NUMBER);
        energyMeter.setCreatedDate(NOW);
        return energyMeter;
    }

    private Evse setEvse() {
        Evse evse = new Evse();
        evse.setId(ID);
        evse.setEvseMake(MAKER2);
        evse.setEvseSn(SERIAL_NUMBER);
        evse.setSeccMac(SECC_MAC);
        evse.setPowerRatings(POWER_RATINGS);
        evse.setCreatedDate(NOW);
        return evse;
    }

    private EvseDto setEvseDto() {
        return EvseDto.builder()
                .evseMake(MAKER2)
                .evseSn(SERIAL_NUMBER)
                .seccMac(SECC_MAC)
                .powerRatings(POWER_RATINGS)
                .build();
    }
}
