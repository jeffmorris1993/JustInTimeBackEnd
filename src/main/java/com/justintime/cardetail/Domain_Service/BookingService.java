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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final BookingRepository bookingRepository;

    public UUID upsertBooking(BookingInformation bookingInformation) {
        CustomerEntity customerEntity = bookingInformation.getCustomer().getCustomerId() != null ?
                customerService.updateCustomer(bookingInformation.getCustomer()) :
                customerService.createCustomer(bookingInformation.getCustomer());

        VehicleEntity vehicleEntity = bookingInformation.getVehicle().getVehicleId() != null ?
                vehicleService.updateVehicle(bookingInformation.getVehicle(), customerEntity) :
                vehicleService.createVehicle(bookingInformation.getVehicle(), customerEntity);

        Optional<BookingEntity> bookingEntity = bookingInformation.getBookingNumber() != null ?
                bookingRepository.findById(bookingInformation.getBookingNumber()) : Optional.empty();
        BookingEntity.BookingEntityBuilder bookingEntityBuilder = bookingEntity.map(BookingEntity::toBuilder)
                .orElse(BookingEntity.builder());
        BookingEntity newBooking = bookingRepository.save(bookingEntityBuilder
                .dateOfService(bookingInformation.getDateOfService())
                .customer(customerEntity)
                .vehicle(vehicleEntity)
                .isSubmitted(bookingInformation.isSubmitted())
                .build()
        );
        return newBooking.getBookingNumber();
    }

    @Transactional
    public List<BookingResponse> getBookings(){
        List<BookingEntity> bookingEntities = bookingRepository.findAll();
        return bookingEntities.stream().map(bookingEntity -> BookingResponse.builder()
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
                .dateOfService(bookingEntity.getDateOfService() != null ?
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(bookingEntity.getDateOfService()) : null)
                .vehicleId(bookingEntity.getVehicle().getVehicleId())
                .year(bookingEntity.getVehicle().getYear())
                .make(bookingEntity.getVehicle().getMake())
                .model(bookingEntity.getVehicle().getModel())
                .build()).collect(Collectors.toList());
    }
}
