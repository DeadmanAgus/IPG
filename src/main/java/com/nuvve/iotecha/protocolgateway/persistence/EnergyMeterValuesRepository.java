package com.nuvve.iotecha.protocolgateway.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nuvve.iotecha.protocolgateway.domains.EnergyMeterValues;

@Repository
public interface EnergyMeterValuesRepository  extends JpaRepository<EnergyMeterValues, Long>{

}
