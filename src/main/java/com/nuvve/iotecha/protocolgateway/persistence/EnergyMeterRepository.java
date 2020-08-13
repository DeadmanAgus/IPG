package com.nuvve.iotecha.protocolgateway.persistence;

import com.nuvve.iotecha.protocolgateway.domains.EnergyMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnergyMeterRepository extends JpaRepository<EnergyMeter, Long> {
    @Override
    @Query("SELECT em FROM EnergyMeter em WHERE em.enabled = true")
    List<EnergyMeter> findAll();

    @Query("SELECT em FROM EnergyMeter em WHERE em.enabled = true and em.id = :id")
    Optional<EnergyMeter> findById(@Param("id")long id);

    Optional<EnergyMeter> findByEvseId(long evseId);
}
