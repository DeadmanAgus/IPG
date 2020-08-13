package com.nuvve.iotecha.protocolgateway.controllers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterValuesDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DispatchController {

    private static final String TOPIC_MESSAGES = "/topic/messages";
    private static final String queueName = "iotecha.queue";

    @Autowired
	private SimpMessagingTemplate template;


    @RabbitListener(queues = queueName)
    public void dispatchResponse(final EnergyMeterValuesDto energyMeterValues) {
        log.info("Message received = " + energyMeterValues.toString());
        template.convertAndSend(TOPIC_MESSAGES, energyMeterValues);
    }
	
}
