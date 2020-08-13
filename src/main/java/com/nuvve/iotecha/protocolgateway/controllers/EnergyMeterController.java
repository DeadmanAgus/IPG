package com.nuvve.iotecha.protocolgateway.controllers;

import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterDto;
import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterValuesDto;
import com.nuvve.iotecha.protocolgateway.services.EnergyMeterService;
import com.nuvve.iotecha.protocolgateway.services.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class EnergyMeterController {

    private static final String TOPIC_MESSAGES = "/topic/messages";
    @Autowired
    EnergyMeterService energyMeterService;

    @Autowired
    RabbitMQService rabbitMQService;

    /**
     * Creates an Energy Meter
     *
     * @param energyMeter
     * @return
     */
    @MessageMapping("/energyMeter/create")
    @SendTo(TOPIC_MESSAGES)
    public EnergyMeterDto createEnergyMeter(EnergyMeterDto energyMeter) {
        EnergyMeterDto energyMeterDto = null;
        try {
            energyMeterDto = energyMeterService.createEnergyMeter(energyMeter);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return energyMeterDto;
    }

    /**
     * Updates an EnergyMeter
     *
     * @param energyMeter
     * @return EvseDto
     */
    @MessageMapping("/energyMeter/update")
    @SendTo(TOPIC_MESSAGES)
    public EnergyMeterDto updateEnergyMeter(EnergyMeterDto energyMeter) {
        EnergyMeterDto energyMeterDto = new EnergyMeterDto();
        try {
            energyMeterDto = energyMeterService.updateEnergyMeter(energyMeter);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return energyMeterDto.setError(exception.getMessage(), 400);
        }
        return energyMeterDto;
    }

    /**
     * Deletes an EnergyMeter
     *
     * @param id
     */
    @MessageMapping("/energyMeter/delete/{id}")
    @SendTo(TOPIC_MESSAGES)
    public EnergyMeterDto deleteByIdEnergyMeter(@DestinationVariable long id) {
        EnergyMeterDto response;
        try {
            response = energyMeterService.deleteEnergyMeterById(id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new EnergyMeterDto().setError(exception.getMessage(), 400);
        }
        return response;
    }

    /**
     * Retrieves an EnergyMeter
     *
     * @param id
     * @return EnergyMeterDto
     */
    @MessageMapping("/energyMeter/get/{id}")
    @SendTo(TOPIC_MESSAGES)
    public EnergyMeterDto getById(@DestinationVariable long id) {
        EnergyMeterDto energyMeterDto = new EnergyMeterDto();
        try {
            energyMeterDto = energyMeterService.findById(id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return energyMeterDto.setError(exception.getMessage(), 400);
        }
        return energyMeterDto;
    }

    /**
     * Retrieves all EnergyMeter
     *
     * @return List<EnergyMeterDto>
     */
    @MessageMapping("/energyMeter/getAll")
    @SendTo(TOPIC_MESSAGES)
    public List<EnergyMeterDto> getAll() {
        List<EnergyMeterDto> energyMeterDtoList = null;
        try {
            energyMeterDtoList = energyMeterService.getAll();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        return energyMeterDtoList;
    }

    /**
     * Creates an Energy Meter Info
     *
     * @param energyMeter
     * @return
     */
    @MessageMapping("/energyMeterInfo/create")
    public void createEnergyMeterInfo(EnergyMeterValuesDto energyMeter) {
        EnergyMeterValuesDto energyMeterDto = null;
        try {
            rabbitMQService.send(energyMeter);
            energyMeterDto = energyMeterService.createEnergyMeterInfo(energyMeter);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
