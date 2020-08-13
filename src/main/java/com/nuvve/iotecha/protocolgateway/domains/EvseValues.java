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
public class EvseValues extends CoreDomain<Long> {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private long transactionId;
    
    @Column(nullable = false)
    private int connectorId;
    
    @Column(nullable = false)
    private int evseNominalAcCapacityUp;
    
    @Column(nullable = false)
    private int evseNominalAcCapacityDown;
    
    @Column(nullable = false)
    private int evseActualAcCapacityDown;
    
    @Column(nullable = false)
    private int evseActualAcCapacityUp;
    
    @Column(nullable = false)
    private int evseEfficiency;
    
    @Column(nullable = false)
    private int evMaxVoltage;
    
    @Column(nullable = false)
    private int evMaxCurrent;
    
    @Column(nullable = false)
    private int evMaxPower;
    
    @Column(nullable = false)
    private int evTargetVoltage;
    
    @Column(nullable = false)
    private int evTargetCurrent;
    
    @Column(nullable = false)
    private int evEnergyCapacity;
    
    @Column(nullable = false)
    private int sessionEnergyIn;
    
    @Column(nullable = false)
    private int sessionEnergyOut;
    
    @Column(nullable = false)
    private int soc;
    
    @Column(nullable = false)
    private int temperatures;
    
    @Column(nullable = false)
    private String evseMode;
    
    @Column(nullable = false)
    private LocalDateTime timeStamp;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evseId", referencedColumnName = "id")
    private Evse evse;
    
    public EvseValues() {
		super();
	}
}
