package com.justintime.cardetail.Application_Service;

import com.justintime.cardetail.Domain_Service.CustomerService;
import com.justintime.cardetail.Domain_Service.VehicleService;
import com.justintime.cardetail.Model.BookingInformation;
import com.justintime.cardetail.Model.Entity.CustomerEntity;
import com.justintime.cardetail.Model.Entity.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CreateBookingService {

    private final CustomerService customerService;
    private VehicleService vehicleService;

    private static final Logger logger = LoggerFactory.getLogger(CreateBookingService.class);

    public void run(BookingInformation bookingInformation){
        logger.info("Starting to create a booking");
        CustomerEntity customerEntity = customerService.createCustomer(bookingInformation.getCustomer());
        VehicleEntity vehicleEntity = vehicleService.createVehicle(bookingInformation.getVehicle(), customerEntity);
    }
}
