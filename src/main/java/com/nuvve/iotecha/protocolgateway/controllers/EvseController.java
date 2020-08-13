package com.nuvve.iotecha.protocolgateway.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.nuvve.iotecha.protocolgateway.dtos.EvseDto;
import com.nuvve.iotecha.protocolgateway.dtos.EvseValuesDto;
import com.nuvve.iotecha.protocolgateway.services.EvseService;
import com.nuvve.iotecha.protocolgateway.services.RabbitMQService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

//import java.security.interfaces.RSAPublicKey;
//import java.util.Calendar;
//import com.auth0.jwk.Jwk;
//import com.auth0.jwk.JwkProvider;
//import com.auth0.jwk.UrlJwkProvider;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;

@Slf4j
@Controller
@Api("/app/evse")
public class EvseController {

    @Autowired
    EvseService evseService;

    @Autowired
    RabbitMQService rabbitMQService;

    /**
     * Creates an EVSE
     *
     * @param evse
     * @return EvseDto
     */
    @MessageMapping("/evse/create")
    @SendTo("/topic/messages")
    @ApiOperation(value="Creates EVSE",
    	notes="Creates EVSE",
    	response=EvseDto.class)
    public EvseDto createEvse(EvseDto evse) {
        EvseDto evseDto = new EvseDto();

        try {
            evseDto = evseService.createEvse(evse);
    		
    		/*
    		 String token = ""; 
    	    DecodedJWT jwt = JWT.decode(token);
    	    JwkProvider provider = new UrlJwkProvider("https://cognito-idp.us-east-1.amazonaws
    	    .com/us-east-1_1LvFLAefr");
    	    Jwk jwk = provider.get(jwt.getKeyId());
    	    Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
    	    algorithm.verify(jwt);
        	        	    
        	 // Check expiration
    	    if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
    	      throw new RuntimeException("Exired token!");
    	    }*/
        } catch (Exception exception) {
            log.error(exception.getMessage());
            evseDto.setError(exception.getMessage(), 400);
        }

        return evseDto;
    }

    /**
     * Updates an EVSE
     *
     * @param evse
     * @return EvseDto
     */
    @MessageMapping("/evse/update")
    @SendTo("/topic/messages")
    public EvseDto updateEvse(EvseDto evse) {
        EvseDto evseDto = new EvseDto();
        try {
            evseDto = evseService.updateEvse(evse);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return evseDto.setError(exception.getMessage(), 400);
        }
        return evseDto;
    }

    /**
     * Deletes an EVSE
     *
     * @param id
     */
    @MessageMapping("/evse/delete/{id}")
    @SendTo("/topic/messages")
    public EvseDto deleteByIdEvse(@DestinationVariable long id) {
        EvseDto response;
        try {
            response = evseService.deleteEvseById(id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new EvseDto().setError(exception.getMessage(), 400);
        }
        return response;
    }

    /**
     * Retrieves an EVSE
     *
     * @param id
     * @return EvseDto
     */
    @MessageMapping("/evse/get/{id}")
    @SendTo("/topic/messages")
    public EvseDto getById(@DestinationVariable long id) {
        EvseDto evseDto = new EvseDto();
        try {
            evseDto = evseService.findById(id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return evseDto.setError(exception.getMessage(), 400);
        }
        return evseDto;
    }

    /**
     * Retrieves all EVSEs
     *
     * @return List<EvseDto>
     */
    @MessageMapping("/evse/getAll")
    @SendTo("/topic/messages")
    public List<EvseDto> getAll() {
        List<EvseDto> evseDtoList = null;
        try {
            evseDtoList = evseService.getAll();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        return evseDtoList;
    }

    /**
     * Creates an EVSE Info
     *
     * @param evse
     * @return EvseDto
     */
    @MessageMapping("/evseInfo/create")
    @SendTo("/topic/messages")
    public EvseValuesDto createEvseInfo(EvseValuesDto evse) {
        EvseValuesDto evseValuesDto = null;
        try {
            evseValuesDto = evseService.createEvseInfo(evse);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            //evseValuesDto.setError(exception.getMessage(), 400);
        }
        return evseValuesDto;
    }
}
