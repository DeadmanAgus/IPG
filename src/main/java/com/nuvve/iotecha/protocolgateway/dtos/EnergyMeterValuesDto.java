package com.nuvve.iotecha.protocolgateway.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EnergyMeterValuesDto extends CoreDto {
    private static final long serialVersionUID = 1L;

    private EnergyMeterDto energyMeter;

    private int voltageA;
    private int voltageB;
    private int voltageC;
    private int currentA;
    private int currentB;
    private int currentC;
    private int activePowerA;
    private int activePowerB;
    private int activePowerC;
    private int totalActivePower;
    private int reactivePowerA;
    private int reactivePowerB;
    private int reactivePowerC;
    private int energyUp;
    private int energyDown;
    private int frequency;
    private int powerFactor;
    private int phaseAngle;
    private String meterTimestamp;

    @Builder
    public EnergyMeterValuesDto(String eventType, int errorCode, String description, LocalDateTime timestamp,
                                EnergyMeterDto energyMeter, int voltageA, int voltageB, int voltageC, int currentA,
								int currentB,
                                int currentC, int activePowerA, int activePowerB, int activePowerC,
								int totalActivePower,
                                int reactivePowerA, int reactivePowerB, int reactivePowerC, int energyUp,
								int energyDown, int frequency,
                                int powerFactor, int phaseAngle, String meterTimestamp) {
        super(eventType, errorCode, description, timestamp);
        this.energyMeter = energyMeter;
        this.voltageA = voltageA;
        this.voltageB = voltageB;
        this.voltageC = voltageC;
        this.currentA = currentA;
        this.currentB = currentB;
        this.currentC = currentC;
        this.activePowerA = activePowerA;
        this.activePowerB = activePowerB;
        this.activePowerC = activePowerC;
        this.totalActivePower = totalActivePower;
        this.reactivePowerA = reactivePowerA;
        this.reactivePowerB = reactivePowerB;
        this.reactivePowerC = reactivePowerC;
        this.energyUp = energyUp;
        this.energyDown = energyDown;
        this.frequency = frequency;
        this.powerFactor = powerFactor;
        this.phaseAngle = phaseAngle;
        this.meterTimestamp = meterTimestamp;
    }

}
