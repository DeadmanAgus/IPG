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
public class DispatchValues extends CoreDomain<Long> {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    private long transactionId;
   
    @Column(nullable = false)
    private String authorizationResult;
    
    @Column(nullable = false)
    private String mode;
    
    @Column(nullable = false)
    private long setpointId; 
    
    @Column(nullable = false)
    private int setpoint; 
    
    @Column(nullable = false)
    private int validity; 
    
    @Column(nullable = false)
    private String defaultProfile;
    
    @Column(nullable = false)
    private LocalDateTime timeStamp;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evseId", referencedColumnName = "id")
    private Evse evse;

	public DispatchValues() {
		super();
	}
}
