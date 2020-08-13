package com.nuvve.iotecha.protocolgateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.nuvve.iotecha.protocolgateway.dtos.PlugUnplugDto;
import com.nuvve.iotecha.protocolgateway.services.EVService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class EVController {

	 @Autowired
	 EVService evService;
	
	 /**
     * Plugs the EV
     * 
     * @param plugUnplugDto
     * @return
     */
    @MessageMapping("/evPlugged")
    @SendTo("/topic/messages")
    public PlugUnplugDto evPlugged(PlugUnplugDto plugUnplugDto) {
    	try {
    		plugUnplugDto = evService.evPlugged(plugUnplugDto);
    	} catch (Exception exception){
    		log.error(exception.getMessage());
    	}
    	
        return plugUnplugDto;
    }
    
    /**
     * Unplugs the EV
     * 
     * @param plugUnplugDto
     * @return
     */
    @MessageMapping("/evUnplugged")
    @SendTo("/topic/messages")
    public PlugUnplugDto evUnplugged(PlugUnplugDto plugUnplugDto) {
    	try {
    		plugUnplugDto = evService.evUnplugged(plugUnplugDto);
    	} catch (Exception exception){
    		log.error(exception.getMessage());
    	}
    	
        return plugUnplugDto;
    }
}
