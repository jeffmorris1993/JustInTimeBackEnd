package com.justintime.cardetail.Domain_Service;

import com.justintime.cardetail.Model.RequestBody.BookingInformation;
import com.justintime.cardetail.Model.Mapper.BookingResponseMapper;
import com.justintime.cardetail.Model.RequestBody.EmailInformation;
import com.justintime.cardetail.Model.Response.BookingResponse;
import com.justintime.cardetail.Model.Entity.*;
import com.justintime.cardetail.Repository.BookingRepository;
import com.justintime.cardetail.Util.Constants;
import com.justintime.cardetail.Util.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final BookingRepository bookingRepository;
    private final BookingResponseMapper bookingResponseMapper;
    private final EmailService emailService;

    public BookingResponse upsertBooking(BookingInformation bookingInformation) {
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
                .baseCost(calculateBaseCost(vehicleEntity.getModel(), bookingInformation.getVehicle().getServiceType(),
                        bookingInformation.getVehicle().getAddOns()))
                .build()
        );
        return bookingResponseMapper.convertToBookingResponse(newBooking);
    }

    @Transactional
    public List<BookingResponse> getBookings(){
        List<BookingEntity> bookingEntities = bookingRepository.findAll();
        return bookingEntities.stream()
                .map(bookingResponseMapper::convertToBookingResponse)
                .collect(Collectors.toList());
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
            bookingEntity.setServiceProviders(emailInformation.getServiceProviders().toString());
        });
    }

    private BigDecimal calculateBaseCost(String model, int serviceType, List<String> addOns) {
        BigDecimal totalCost = BigDecimal.ZERO;
        boolean isCarOrSUV = model.toLowerCase().contains("car") || model.toLowerCase().contains("suv");
        boolean isTruckOrVan = model.toLowerCase().contains("truck") || model.toLowerCase().contains("van");
        if (isCarOrSUV) {
            totalCost = totalCost.add(Constants.ServiceType.getCarSUVCost(serviceType));
        }
        else if (isTruckOrVan) {
            totalCost = totalCost.add(Constants.ServiceType.getTruckVanCost(serviceType));
        }
        for (String addOn : addOns) {
            if (isCarOrSUV) {
                totalCost = totalCost.add(Constants.AddOnType.valueOf(addOn).getCarSUVCost());
            }
            else if (isTruckOrVan) {
                totalCost = totalCost.add(Constants.AddOnType.valueOf(addOn).getTruckVanCost());
            }
        }
        totalCost = totalCost.setScale(2, RoundingMode.HALF_UP);
        return totalCost;
    }
}
