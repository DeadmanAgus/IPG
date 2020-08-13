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
public class PlugUnplug extends CoreDomain<Long> {
    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    private long transactionId;
    
    private Integer connectorId;
    
    private String evccMac;
    
    @Column(nullable = false)
    private String eventType;
    
    @Column(nullable = false)
    private LocalDateTime timeStamp;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evseId", referencedColumnName = "id")
    private Evse evse;
}
