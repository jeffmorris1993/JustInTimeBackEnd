package com.justintime.cardetail.Domain_Service;

import com.justintime.cardetail.Model.Entity.CustomerEntity;
import com.justintime.cardetail.Model.Entity.VehicleEntity;
import com.justintime.cardetail.Model.Entity.VehicleInspectionEntity;
import com.justintime.cardetail.Model.Vehicle;
import com.justintime.cardetail.Model.VehicleInspection;
import com.justintime.cardetail.Repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleEntity createVehicle(Vehicle vehicle, CustomerEntity customerEntity,
                                       VehicleInspection vehicleInspection){
        VehicleInspection inspection = null;
        if (vehicleInspection != null) {
            inspection = VehicleInspectionEntity.builder().inspectionValueTypeId(vehicleInspection.ge());
        }
        return vehicleRepository.save(VehicleEntity.builder()
                .make(vehicle.getMake())
                .year(vehicle.getYear())
                .model(vehicle.getModel())
                .serviceTypeId(vehicle.getServiceType())
                .customerEntity(customerEntity)
                .vehicleInspectionEntity()
                .build());
    }

    public VehicleEntity updateVehicle(Vehicle vehicle, CustomerEntity customerEntity){
        Optional<VehicleEntity> vehicleEntity = vehicleRepository.findById(vehicle.getVehicleId());

        return vehicleEntity.map(v -> vehicleRepository.save(v.toBuilder()
                .make(vehicle.getMake())
                .year(vehicle.getYear())
                .model(vehicle.getModel())
                .serviceTypeId(vehicle.getServiceType())
                .customerEntity(customerEntity)
                .build())
        ).orElse(null);
    }
}
