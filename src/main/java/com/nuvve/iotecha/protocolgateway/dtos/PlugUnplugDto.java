package com.nuvve.iotecha.protocolgateway.dtos;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlugUnplugDto extends CoreDto {

	private static final long serialVersionUID = 1L;
	
	private EvseDto evse;
    private long transactionId;
    private Integer connectorId;
    private String evccMac;
    private String eventType;
    
    @Builder
    public PlugUnplugDto(String eventType, int errorCode, String description, LocalDateTime timestamp,
    		long transactionId, Integer connectorId, String evccMac, EvseDto evse) {
		super(eventType, errorCode, description, timestamp);
		
		this.transactionId = transactionId;
		this.connectorId = connectorId;
		this.evccMac = evccMac;
		this.eventType = eventType;
		this.evse = evse;
	}
}

