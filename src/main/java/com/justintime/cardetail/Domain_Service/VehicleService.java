package com.justintime.cardetail.Domain_Service;

import com.justintime.cardetail.Model.Entity.CustomerEntity;
import com.justintime.cardetail.Model.Entity.VehicleEntity;
import com.justintime.cardetail.Model.Vehicle;
import com.justintime.cardetail.Repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleEntity createVehicle(Vehicle vehicle, CustomerEntity customerEntity){
        return vehicleRepository.save(VehicleEntity.builder().make(vehicle.getMake()).year(vehicle.getYear())
                .model(vehicle.getModel()).serviceTypeId(vehicle.getServiceType()).customerEntity(customerEntity).build());
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
