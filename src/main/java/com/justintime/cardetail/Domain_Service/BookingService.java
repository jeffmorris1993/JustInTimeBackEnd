package com.justintime.cardetail.Domain_Service;

import com.justintime.cardetail.Model.RequestBody.BookingInformation;
import com.justintime.cardetail.Model.RequestBody.EmailInformation;
import com.justintime.cardetail.Model.Entity.*;
import com.justintime.cardetail.Repository.BookingRepository;
import com.justintime.cardetail.Util.EmailService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class BookingService {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final BookingRepository bookingRepository;
    private final EmailService emailService;

    public BookingEntity upsertBooking(BookingInformation bookingInformation) {
        CustomerEntity customerEntity = customerService.upsertCustomer(bookingInformation.getCustomer());

        VehicleEntity vehicleEntity = vehicleService.upsertVehicle(bookingInformation.getVehicle(), customerEntity, bookingInformation.getDateOfService());

        Optional<BookingEntity> bookingEntity = bookingInformation.getBookingNumber() != null ?
                bookingRepository.findById(bookingInformation.getBookingNumber()) : Optional.empty();
        BookingEntity.BookingEntityBuilder bookingEntityBuilder = bookingEntity.map(BookingEntity::toBuilder)
                .orElse(BookingEntity.builder());
        return bookingRepository.save(bookingEntityBuilder
                .dateOfService(bookingInformation.getDateOfService())
                .customer(customerEntity)
                .vehicle(vehicleEntity)
                .isSubmitted(bookingInformation.isSubmitted())
                .baseCost(calculateBaseCost(vehicleEntity.getCost(), vehicleEntity.getAddOnEntities()))
                .build()
        );
    }

    @Transactional
    public Page<BookingEntity> getBookings(Pageable pageable, UUID bookingNumber, String customerFirstName,
                                           String customerLastName, Date dateOfService) {
        if (bookingNumber != null) {
            return bookingRepository.findByBookingNumber(bookingNumber, pageable);
        } else if ((customerFirstName != null && !customerFirstName.isEmpty()) ||
                (customerLastName != null && !customerLastName.isEmpty())) {
            return bookingRepository.findByCustomerFirstNameOrLastNameContainingIgnoreCase(customerFirstName,
                    customerLastName, pageable);
        } else if (dateOfService != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateOfService);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date startDate = calendar.getTime();

            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            Date endDate = calendar.getTime();

            return bookingRepository.findByDateOfServiceBetween(startDate, endDate, pageable);
        } else {
            return bookingRepository.findAll(pageable);
        }
    }

    public BigDecimal calculateTotalCost(UUID bookingNumber, BigDecimal tip){
        Optional<BookingEntity> optionalBookingEntity = bookingRepository.findById(bookingNumber);
        return optionalBookingEntity.map(bookingEntity -> {
            BigDecimal cost = BigDecimal.ZERO
                    .add(bookingEntity.getBaseCost())
                    .add(tip);
            bookingEntity.setTotalCost(cost);
            bookingEntity.setTip(tip);
            bookingRepository.save(bookingEntity);
            return cost;
        }).orElse(BigDecimal.ZERO);
    }

    @Transactional
    public void sendEmail(EmailInformation emailInformation) {
        Optional<BookingEntity> optionalBookingEntity = bookingRepository.findById(emailInformation.getBookingNumber());
        optionalBookingEntity.ifPresent(bookingEntity -> {
            emailService.sendEmail(bookingEntity, emailInformation.getNotes(), emailInformation.getServiceProviders());
            bookingEntity.setNotes(emailInformation.getNotes());
            bookingEntity.setServiceProviders(String.join(",", emailInformation.getServiceProviders()));
        });
    }

    private BigDecimal calculateBaseCost(BigDecimal mainServiceCost, List<AddOnEntity> addOnEntities) {
        BigDecimal totalCost = mainServiceCost;
        for (AddOnEntity addOnEntity : addOnEntities) {
            totalCost = totalCost.add(addOnEntity.getCost());
        }
        return totalCost;
    }
}
