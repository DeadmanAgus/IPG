package com.nuvve.iotecha.protocolgateway.dtos;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvseValuesDto extends CoreDto{
	
	private static final long serialVersionUID = 1L;
	
	private EvseDto evse;
	
    private long transactionId;
    private int connectorId;
    private int evseNominalAcCapacityUp;
    private int evseNominalAcCapacityDown;
    private int evseActualAcCapacityDown;
    private int evseActualAcCapacityUp;
    private int evseEfficiency;
    private int evMaxVoltage;
    private int evMaxCurrent;
    private int evMaxPower;
    private int evTargetVoltage;
    private int evTargetCurrent;
    private int evEnergyCapacity;
    private int sessionEnergyIn;
    private int sessionEnergyOut;
    private int soc;
    private int temperatures;
    private String evseMode;
    private String evseTimestamp;
    
    @Builder
	public EvseValuesDto(String eventType, int errorCode, String description, LocalDateTime timestamp, EvseDto evse,
			long transactionId, int connectorId, int evseNominalAcCapacityUp, int evseNominalAcCapacityDown,
			int evseActualAcCapacityDown, int evseActualAcCapacityUp, int evseEfficiency, int evMaxVoltage,
			int evMaxCurrent, int evMaxPower, int evTargetVoltage, int evTargetCurrent, int evEnergyCapacity,
			int sessionEnergyIn, int sessionEnergyOut, int soc, int temperatures, String evseMode, String evseTimestamp) {
		super(eventType, errorCode, description, timestamp);
		this.evse = evse;
		this.transactionId = transactionId;
		this.connectorId = connectorId;
		this.evseNominalAcCapacityUp = evseNominalAcCapacityUp;
		this.evseNominalAcCapacityDown = evseNominalAcCapacityDown;
		this.evseActualAcCapacityDown = evseActualAcCapacityDown;
		this.evseActualAcCapacityUp = evseActualAcCapacityUp;
		this.evseEfficiency = evseEfficiency;
		this.evMaxVoltage = evMaxVoltage;
		this.evMaxCurrent = evMaxCurrent;
		this.evMaxPower = evMaxPower;
		this.evTargetVoltage = evTargetVoltage;
		this.evTargetCurrent = evTargetCurrent;
		this.evEnergyCapacity = evEnergyCapacity;
		this.sessionEnergyIn = sessionEnergyIn;
		this.sessionEnergyOut = sessionEnergyOut;
		this.soc = soc;
		this.temperatures = temperatures;
		this.evseMode = evseMode;
		this.evseTimestamp = evseTimestamp;
	}
    
    
}
