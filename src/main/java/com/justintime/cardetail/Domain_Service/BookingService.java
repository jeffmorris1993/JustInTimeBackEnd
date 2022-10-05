package com.justintime.cardetail.Domain_Service;

import com.justintime.cardetail.Model.BookingInformation;
import com.justintime.cardetail.Model.BookingResponse;
import com.justintime.cardetail.Model.Entity.BookingEntity;
import com.justintime.cardetail.Model.Entity.CustomerEntity;
import com.justintime.cardetail.Model.Entity.VehicleEntity;
import com.justintime.cardetail.Repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Transactional
    public List<BookingResponse> getBookings(){
        List<BookingEntity> bookingEntities = bookingRepository.findAll();
        return bookingEntities.stream().map(bookingEntity -> BookingResponse.builder()
                .bookingNumber(bookingEntity.getBookingNumber())
                .firstName(bookingEntity.getCustomer().getFirstName())
                .lastName(bookingEntity.getCustomer().getLastName())
                .email(bookingEntity.getCustomer().getEmail())
                .phone(bookingEntity.getCustomer().getPhone())
                .address(bookingEntity.getCustomer().getStreetAddress())
                .city(bookingEntity.getCustomer().getCity())
                .zip(bookingEntity.getCustomer().getZip())
                .serviceType(bookingEntity.getVehicle().getServiceTypeId())
                .dateOfService(bookingEntity.getDateOfService() != null ?
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(bookingEntity.getDateOfService()) : null)
                .year(bookingEntity.getVehicle().getYear())
                .make(bookingEntity.getVehicle().getMake())
                .model(bookingEntity.getVehicle().getModel())
                .build()).collect(Collectors.toList());
    }
}
