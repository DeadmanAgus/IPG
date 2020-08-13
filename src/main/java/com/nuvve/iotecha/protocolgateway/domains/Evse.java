package com.nuvve.iotecha.protocolgateway.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;


@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE evse SET enabled = false WHERE id = ?", check = ResultCheckStyle.COUNT)
public class Evse extends CoreDomain<Long> {

    private static final long serialVersionUID = 1L;
    @Column(nullable = false)
    private String evseSn;
    @Column(nullable = false)
    private String evseMake;
    @Column(nullable = false)
    private String seccMac;
    @Column(nullable = false)
    private String powerRatings;

    public Evse() {
        super();
    }

    @OneToMany(
        mappedBy = "evse", 
        cascade = CascadeType.ALL, 
    	fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<PlugUnplug> plugUnplugList = new ArrayList<>();
    
    @OneToMany(
        mappedBy = "evse", 
        cascade = CascadeType.ALL, 
    	fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<EvseValues> evseValues = new ArrayList<>();
    
    @OneToMany(
        mappedBy = "evse", 
        cascade = CascadeType.ALL, 
    	fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<DispatchValues> dispatchValues = new ArrayList<>();
    
}
