package com.nuvve.iotecha.protocolgateway.services;

import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterDto;
import com.nuvve.iotecha.protocolgateway.dtos.EnergyMeterValuesDto;
import com.nuvve.iotecha.protocolgateway.dtos.EvseDto;
import com.nuvve.iotecha.protocolgateway.dtos.EvseValuesDto;
import com.nuvve.iotecha.protocolgateway.dtos.PlugUnplugDto;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${nuvve.iotecha.rabbitmq.exchange}")
    private String exchange;

    @Value("${nuvve.iotecha.rabbitmq.routingkey}")
    private String routingkey;

    private final String queueName = "iotecha.queue";

    public void send(EvseValuesDto evseValues) {
        amqpTemplate.convertAndSend(exchange, routingkey, evseValues);
        System.out.println("Send msg = " + evseValues);
    }

    public void send(EnergyMeterValuesDto energyMeterValues) {
        amqpTemplate.convertAndSend(exchange, routingkey, energyMeterValues);
        System.out.println("Send msg = " + energyMeterValues);
    }
    
    public void send(PlugUnplugDto plugUnplugDto) {
        amqpTemplate.convertAndSend(exchange, routingkey, plugUnplugDto);
        System.out.println("Send msg = " + plugUnplugDto);
    }

    /**
    @RabbitListener(queues = "${nuvve.iotecha.rabbitmq.routingkey}")
    public void read(final EnergyMeterValuesDto energyMeterValues){
        System.out.println("Recibe message = " + energyMeterValues.toString());

    }
    */

}
