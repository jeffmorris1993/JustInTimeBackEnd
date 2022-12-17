package com.justintime.cardetail.Model.Mapper;

import com.justintime.cardetail.Model.Entity.AddOnEntity;
import com.justintime.cardetail.Model.Entity.BookingEntity;
import com.justintime.cardetail.Model.Entity.VehicleInspectionEntity;
import com.justintime.cardetail.Model.Response.BookingResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
@AllArgsConstructor
public class BookingResponseMapper {

   public BookingResponse convertToBookingResponse(BookingEntity bookingEntity){
       return BookingResponse.builder()
               .bookingNumber(bookingEntity.getBookingNumber())
               .isSubmitted(bookingEntity.isSubmitted())
               .customerId(bookingEntity.getCustomer().getCustomerId())
               .firstName(bookingEntity.getCustomer().getFirstName())
               .lastName(bookingEntity.getCustomer().getLastName())
               .email(bookingEntity.getCustomer().getEmail())
               .phone(bookingEntity.getCustomer().getPhone())
               .address(bookingEntity.getCustomer().getStreetAddress())
               .city(bookingEntity.getCustomer().getCity())
               .zip(bookingEntity.getCustomer().getZip())
               .serviceType(bookingEntity.getVehicle().getServiceTypeId())
               .addOns(bookingEntity.getVehicle().getAddOnEntities() != null ?
                       bookingEntity.getVehicle().getAddOnEntities().stream()
                               .map(AddOnEntity::getAddOnId)
                               .filter(Objects::nonNull)
                               .collect(Collectors.toList()) : null)
               .dateOfService(bookingEntity.getDateOfService() != null ?
                       new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(bookingEntity.getDateOfService()) : null)
               .vehicleId(bookingEntity.getVehicle().getVehicleId())
               .year(bookingEntity.getVehicle().getYear())
               .make(bookingEntity.getVehicle().getMake())
               .model(bookingEntity.getVehicle().getModel())
               .exteriorInspection(bookingEntity.getVehicle().getVehicleInspectionEntity() != null ?
                       bookingEntity.getVehicle().getVehicleInspectionEntity().stream()
                               .map(VehicleInspectionEntity::getExternalInspectionValueTypeId)
                               .filter(Objects::nonNull)
                               .collect(Collectors.toList()) : null)
               .interiorInspection(bookingEntity.getVehicle().getVehicleInspectionEntity() != null ?
                       bookingEntity.getVehicle().getVehicleInspectionEntity().stream()
                               .map(VehicleInspectionEntity::getInternalInspectionValueTypeId)
                               .filter(Objects::nonNull)
                               .collect(Collectors.toList()) : null)
               .baseCost(bookingEntity.getBaseCost())
               .totalCost(bookingEntity.getTotalCost())
               .tip(bookingEntity.getTip())
               .build();

   }

}
