package com.nuvve.iotecha.protocolgateway.services;

import com.nuvve.iotecha.protocolgateway.domains.EnergyMeter;
import com.nuvve.iotecha.protocolgateway.domains.Evse;
import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterDto;
import com.nuvve.iotecha.protocolgateway.dtos.EventType;
import com.nuvve.iotecha.protocolgateway.dtos.EvseDto;
import com.nuvve.iotecha.protocolgateway.exceptions.ProtocolGatewayException;
import com.nuvve.iotecha.protocolgateway.persistence.EnergyMeterRepository;
import com.nuvve.iotecha.protocolgateway.persistence.EvseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class EnergyMeterServiceTest {

    public static final String EVSE_NOT_FOUND = "EVSE not found";
    private static final String MAKER = "maker";
    private static final String MAKER2 = "maker2";
    private static final String SERIAL_NUMBER = "serialNumber";
    private static final String MODEL = "model";
    private static final String POWER_RATINGS = "powerRatings";
    private static final String SECC_MAC = "seccMac";
    private static final long ID = 0l;
    private static final LocalDateTime NOW = LocalDateTime.now();
    private final String EVSE_ID_MUST_BE_UNIQUE = "EVSE id already exist and must be unique";
    private final String ENERGY_METER_DELETED_CORRECTLY = "energyMeter deleted correctly";

    @Autowired
    private EnergyMeterService service;

    @MockBean
    private EnergyMeterRepository repositoryMock;

    @MockBean
    private EvseRepository evseRepositoryMock;

    private EnergyMeter energyMeter;
    private EnergyMeterDto dto;
    private EvseDto evseDto;
    private Evse evse;

    @BeforeEach
    public void setup() {
        setEvse();
        setEvseDto();
        setEnergyMeter();
        setEnergyMeterDto();
    }

    @Test
    public void energyMeterCreateTest() throws ProtocolGatewayException {
        Mockito.when(evseRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(evse));
        Mockito.when(repositoryMock.findByEvseId(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(repositoryMock.save(Mockito.any())).thenReturn(energyMeter);
        EnergyMeterDto response = service.createEnergyMeter(dto);
        Assertions.assertEquals(energyMeter.getMeterMake(), response.getMeterMake());
        Assertions.assertEquals(energyMeter.getMeterModel(), response.getMeterModel());
        Assertions.assertEquals(energyMeter.getMeterSn(), response.getMeterSn());
    }

    @Test
    public void energyMeterUpdateTest() throws ProtocolGatewayException {
        Mockito.when(repositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(energyMeter));
        Mockito.when(evseRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(evse));
        Mockito.when(repositoryMock.findByEvseId(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(repositoryMock.save(Mockito.any())).thenReturn(energyMeter);
        EnergyMeterDto response = service.updateEnergyMeter(dto);
        Assertions.assertEquals(energyMeter.getMeterMake(), response.getMeterMake());
        Assertions.assertEquals(energyMeter.getMeterModel(), response.getMeterModel());
        Assertions.assertEquals(energyMeter.getMeterSn(), response.getMeterSn());
    }

    @Test
    public void energyMeterDeleteTest() throws ProtocolGatewayException {
        EnergyMeterDto response = service.deleteEnergyMeterById(ID);
        Assertions.assertEquals(ID, response.getMeterId());
        Assertions.assertEquals(EventType.RESPONSE.name(), response.getEventType());
        Assertions.assertEquals(ENERGY_METER_DELETED_CORRECTLY, response.getDescription());
    }

    @Test
    public void energyMeterFindeByIdTest() throws ProtocolGatewayException {
        Mockito.when(repositoryMock.findById(ID)).thenReturn(Optional.of(energyMeter));
        EnergyMeterDto response = service.findById(ID);
        Assertions.assertEquals(energyMeter.getMeterMake(), response.getMeterMake());
        Assertions.assertEquals(energyMeter.getMeterModel(), response.getMeterModel());
        Assertions.assertEquals(energyMeter.getMeterSn(), response.getMeterSn());
    }

    @Test
    public void energyMeterFindeAllTest() throws ProtocolGatewayException {
        Mockito.when(repositoryMock.findAll()).thenReturn(Arrays.asList(energyMeter));
        EnergyMeterDto response = service.getAll().get(0);
        Assertions.assertEquals(energyMeter.getMeterMake(), response.getMeterMake());
        Assertions.assertEquals(energyMeter.getMeterModel(), response.getMeterModel());
        Assertions.assertEquals(energyMeter.getMeterSn(), response.getMeterSn());
    }

    @Test
    public void energyMeterCreateWithEvseNotFoundExceptionTest() {
        Mockito.when(evseRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        ProtocolGatewayException thrown = Assertions.assertThrows(
                ProtocolGatewayException.class,
                () -> service.createEnergyMeter(dto),
                "Expected createEnergyMeter() to throw, EVSE not found"
        );
        Assertions.assertTrue(thrown.getMessage().contains(EVSE_NOT_FOUND));
    }

    @Test
    public void energyMeterCreateWithEvseMustBeUniqueExceptionTest() {
        Mockito.when(evseRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(evse));
        Mockito.when(repositoryMock.findByEvseId(Mockito.anyLong())).thenReturn(Optional.of(energyMeter));
        ProtocolGatewayException thrown = Assertions.assertThrows(
                ProtocolGatewayException.class,
                () -> service.createEnergyMeter(dto),
                "Expected createEnergyMeter() to throw, EVSE id already exist and must be unique"
        );
        Assertions.assertTrue(thrown.getMessage().contains(EVSE_ID_MUST_BE_UNIQUE));
    }

    private void setEnergyMeter() {
        energyMeter = new EnergyMeter();
        energyMeter.setId(ID);
        energyMeter.setEvse(evse);
        energyMeter.setMeterMake(MAKER);
        energyMeter.setMeterModel(MODEL);
        energyMeter.setMeterSn(SERIAL_NUMBER);
        energyMeter.setCreatedDate(NOW);
    }

    private void setEnergyMeterDto() {
        dto = EnergyMeterDto.builder()
                .evse(evseDto)
                .meterMake(MAKER)
                .meterModel(MODEL)
                .meterSn(SERIAL_NUMBER)
                .build();
    }

    private void setEvse() {
        evse = new Evse();
        evse.setId(ID);
        evse.setEvseMake(MAKER2);
        evse.setEvseSn(SERIAL_NUMBER);
        evse.setSeccMac(SECC_MAC);
        evse.setPowerRatings(POWER_RATINGS);
        evse.setCreatedDate(NOW);
    }

    private void setEvseDto() {
        evseDto = EvseDto.builder()
                .evseMake(MAKER2)
                .evseSn(SERIAL_NUMBER)
                .seccMac(SECC_MAC)
                .powerRatings(POWER_RATINGS)
                .build();
    }

}
