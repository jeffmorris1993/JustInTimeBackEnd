package com.justintime.cardetail.Domain_Service;

import com.justintime.cardetail.Model.Entity.AddOnEntity;
import com.justintime.cardetail.Model.Entity.CustomerEntity;
import com.justintime.cardetail.Model.Entity.VehicleEntity;
import com.justintime.cardetail.Model.Entity.VehicleInspectionEntity;
import com.justintime.cardetail.Model.Vehicle;
import com.justintime.cardetail.Model.VehicleInspection;
import com.justintime.cardetail.Repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleEntity createVehicle(Vehicle vehicle, CustomerEntity customerEntity){
        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .make(vehicle.getMake())
                .year(vehicle.getYear())
                .model(vehicle.getModel())
                .serviceTypeId(vehicle.getServiceType())
                .customerEntity(customerEntity)
                .build();
        setInspection(vehicle.getVehicleInspection(), vehicleEntity);
        setAddOns(vehicle.getAddOns(), vehicleEntity);

        return vehicleRepository.save(vehicleEntity);
    }

    public VehicleEntity updateVehicle(Vehicle vehicle, CustomerEntity customerEntity){
        Optional<VehicleEntity> optionalVehicleEntity = vehicleRepository.findById(vehicle.getVehicleId());

        return optionalVehicleEntity.map(vehicleEntity -> {
            VehicleEntity updatedVehicleEntity = vehicleEntity.toBuilder()
                    .make(vehicle.getMake())
                    .year(vehicle.getYear())
                    .model(vehicle.getModel())
                    .serviceTypeId(vehicle.getServiceType())
                    .customerEntity(customerEntity)
                    .build();
            setInspection(vehicle.getVehicleInspection(), updatedVehicleEntity);
            setAddOns(vehicle.getAddOns(), updatedVehicleEntity);
            return vehicleRepository.save(updatedVehicleEntity);
        }).orElse(null);
    }

    private void setAddOns(List<String> addOns, VehicleEntity vehicleEntity){
        List<AddOnEntity> addOnEntities = addOns.stream().map(addOn -> AddOnEntity.builder()
                .addOnId(addOn)
                .vehicle(vehicleEntity)
                .build()).collect(Collectors.toList());
        vehicleEntity.setAddOnEntities(addOnEntities);
    }

    private void setInspection(VehicleInspection vehicleInspection, VehicleEntity vehicleEntity) {
        var vehicleInspectionEntities = new ArrayList<VehicleInspectionEntity>(Collections.emptyList());
        if (vehicleInspection != null) {
            Iterator<String> exteriorIterator = vehicleInspection.getExterior().iterator();
            Iterator<String> interiorIterator = vehicleInspection.getInterior().iterator();
            while (exteriorIterator.hasNext() && interiorIterator.hasNext()) {
                vehicleInspectionEntities.add(VehicleInspectionEntity.builder()
                        .externalInspectionValueTypeId(exteriorIterator.next())
                        .internalInspectionValueTypeId(interiorIterator.next())
                        .vehicle(vehicleEntity)
                        .build());
            }
            while (exteriorIterator.hasNext()) {
                vehicleInspectionEntities.add(VehicleInspectionEntity.builder()
                        .externalInspectionValueTypeId(exteriorIterator.next())
                        .internalInspectionValueTypeId(null)
                        .vehicle(vehicleEntity)
                        .build());
            }
            while (interiorIterator.hasNext()) {
                vehicleInspectionEntities.add(VehicleInspectionEntity.builder()
                        .externalInspectionValueTypeId(null)
                        .internalInspectionValueTypeId(interiorIterator.next())
                        .vehicle(vehicleEntity)
                        .build());
            }
            vehicleEntity.setVehicleInspectionEntity(vehicleInspectionEntities);
        }
    }
}
