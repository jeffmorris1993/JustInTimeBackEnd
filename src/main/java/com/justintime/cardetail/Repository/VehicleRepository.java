package com.justintime.cardetail.Repository;

import com.justintime.cardetail.Model.Entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID> {
}
