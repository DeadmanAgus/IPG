package com.nuvve.iotecha.protocolgateway.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nuvve.iotecha.protocolgateway.domains.EvseValues;

@Repository
public interface EvseValuesRepository extends JpaRepository<EvseValues, Long> {}