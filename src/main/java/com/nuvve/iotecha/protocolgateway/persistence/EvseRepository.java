package com.nuvve.iotecha.protocolgateway.persistence;

import com.nuvve.iotecha.protocolgateway.domains.Evse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvseRepository extends JpaRepository<Evse, Long> {

    @Override
    @Query("SELECT e FROM Evse e WHERE e.enabled = true")
    List<Evse> findAll();

    @Query("SELECT e FROM Evse e WHERE e.enabled = true and e.id = :id")
    Optional<Evse> findById(@Param("id") long id);
}
