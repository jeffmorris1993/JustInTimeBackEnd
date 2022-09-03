package com.justintime.cardetail.Domain_Service;

import com.justintime.cardetail.Model.BookingInformation;
import com.justintime.cardetail.Model.Entity.BookingEntity;
import com.justintime.cardetail.Model.Entity.CustomerEntity;
import com.justintime.cardetail.Model.Entity.VehicleEntity;
import com.justintime.cardetail.Repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BookingService {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final BookingRepository bookingRepository;

    public UUID createBooking(BookingInformation bookingInformation){
        CustomerEntity customerEntity = customerService.createCustomer(bookingInformation.getCustomer());
        VehicleEntity vehicleEntity = vehicleService.createVehicle(bookingInformation.getVehicle(), customerEntity);
        BookingEntity bookingEntity = bookingRepository.save(BookingEntity.builder()
                .dateOfService(bookingInformation.getDateOfService())
                .customer(customerEntity)
                .vehicle(vehicleEntity)
                .build()
        );
        return bookingEntity.getBookingNumber();
    }
}
