package com.nuvve.iotecha.protocolgateway.domains;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE energyMeter SET enabled = false WHERE id = ?", check = ResultCheckStyle.COUNT)
public class EnergyMeter extends CoreDomain<Long> {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String meterSn;

    @Column(nullable = false)
    private String meterMake;

    @Column(nullable = false)
    private String meterModel;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evseId", referencedColumnName = "id")
    private Evse evse;

    @OneToMany(
        mappedBy = "energyMeter", 
        cascade = CascadeType.ALL, 
    	fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<EnergyMeterValues> energyMeterValues = new ArrayList<>();
    
    public EnergyMeter() {
        super();
    }
}
