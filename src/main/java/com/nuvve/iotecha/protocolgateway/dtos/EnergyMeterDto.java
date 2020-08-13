package com.nuvve.iotecha.protocolgateway.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EnergyMeterDto extends CoreDto {
	private static final long serialVersionUID = 1L;
	
	private long energyMeterId;
    private EvseDto evse;
    private long meterId;
    private String meterMake;
    private String meterModel;
    private String meterSn;

    public EnergyMeterDto() {
        super();
    }

    @Builder
    public EnergyMeterDto(String eventType, int errorCode, String description, LocalDateTime timestamp,
                          long energyMeterId, EvseDto evse, long meterId, String meterMake, String meterModel,
                          String meterSn) {
        super(eventType, errorCode, description, timestamp);
        this.evse = evse;
        this.meterId = meterId;
        this.meterMake = meterMake;
        this.meterModel = meterModel;
        this.meterSn = meterSn;

    }

    public EnergyMeterDto setError(String error, int errorCode){
        return EnergyMeterDto.builder()
                .description(error)
                .errorCode(errorCode)
                .eventType(EventType.ERROR.name())
                .build();
    }

}
