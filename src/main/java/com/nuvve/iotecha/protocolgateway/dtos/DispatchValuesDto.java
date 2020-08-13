package com.nuvve.iotecha.protocolgateway.dtos;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DispatchValuesDto extends CoreDto{

	private static final long serialVersionUID = 1L;

	private EvseDto evse;
    private long transactionId;
    private String authorizationResult;
    private String mode;
    private long setpointId; 
    private int setpoint; 
    private int validity; 
    private String defaultProfile;
    private LocalDateTime timeStamp;
    
    @Builder
	public DispatchValuesDto(String eventType, int errorCode, String description, LocalDateTime timestamp, EvseDto evse,
			long transactionId, String authorizationResult, String mode, long setpointId, int setpoint, int validity,
			String defaultProfile, LocalDateTime timeStamp2) {
		super(eventType, errorCode, description, timestamp);
		this.evse = evse;
		this.transactionId = transactionId;
		this.authorizationResult = authorizationResult;
		this.mode = mode;
		this.setpointId = setpointId;
		this.setpoint = setpoint;
		this.validity = validity;
		this.defaultProfile = defaultProfile;
		timeStamp = timeStamp2;
	}
}
