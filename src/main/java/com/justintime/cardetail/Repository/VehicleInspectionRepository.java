package com.justintime.cardetail.Repository;

import com.justintime.cardetail.Model.Entity.VehicleInspectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleInspectionRepository extends JpaRepository<VehicleInspectionEntity, UUID> {
}
