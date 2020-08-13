package com.nuvve.iotecha.protocolgateway.domains;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EnergyMeterValues extends CoreDomain<Long> {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private long transactionId;
    
    @Column(nullable = false)
    private int voltageA;
    
    @Column(nullable = false)
    private int voltageB;
    
    @Column(nullable = false)
    private int voltageC;
    
    @Column(nullable = false)
    private int currentA;
    
    @Column(nullable = false)
    private int currentB;
    
    @Column(nullable = false)
    private int currentC;
    
    @Column(nullable = false)
    private int activePowerA;
    
    @Column(nullable = false)
    private int activePowerB;
    
    @Column(nullable = false)
    private int activePowerC;
    
    @Column(nullable = false)
    private int totalActivePower;
    
    @Column(nullable = false)
    private int reactivePowerA;
    
    @Column(nullable = false)
    private int reactivePowerB;
    
    @Column(nullable = false)
    private int reactivePowerC;
    
    @Column(nullable = false)
    private int energyUp;
    
    @Column(nullable = false)
    private int energyDown;
    
    @Column(nullable = false)
    private int frequency;
    
    @Column(nullable = false)
    private int powerFactor;
    
    @Column(nullable = false)
    private int phaseAngle;
    
    @Column(nullable = false)
    private LocalDateTime timeStamp;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "energyMeterId", referencedColumnName = "id")
    private EnergyMeter energyMeter;

	public EnergyMeterValues() {
		super();
	}
}
