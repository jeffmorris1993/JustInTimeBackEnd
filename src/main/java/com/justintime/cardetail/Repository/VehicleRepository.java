package com.justintime.cardetail.Repository;

import com.justintime.cardetail.Model.Entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.sql.Timestamp;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID> {
    Optional<VehicleEntity> findByMakeAndModelAndYearAndDateOfService(String make, String model, int year, Timestamp dateOfService);
}
