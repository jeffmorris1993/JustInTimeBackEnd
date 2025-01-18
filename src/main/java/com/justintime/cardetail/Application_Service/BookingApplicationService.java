package com.justintime.cardetail.Application_Service;

import com.justintime.cardetail.Domain_Service.BookingService;
import com.justintime.cardetail.Model.Entity.BookingEntity;
import com.justintime.cardetail.Model.Mapper.BookingResponseMapper;
import com.justintime.cardetail.Model.RequestBody.BookingInformation;
import com.justintime.cardetail.Model.RequestBody.CostInformation;
import com.justintime.cardetail.Model.RequestBody.EmailInformation;
import com.justintime.cardetail.Model.Response.BookingResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class BookingApplicationService {

    private BookingService bookingService;
    private final BookingResponseMapper bookingResponseMapper;

    private static final Logger logger = LoggerFactory.getLogger(BookingApplicationService.class);

    public BookingResponse upsertBooking(BookingInformation bookingInformation){
        logger.info("Starting to create a booking");
        BookingEntity newBooking = bookingService.upsertBooking(bookingInformation);
        return bookingResponseMapper.convertToBookingResponse(newBooking);
    }

    public Page<BookingResponse> getBookings(Pageable pageable, UUID bookingNumber, String customerFirstName,
                                             String customerLastName, Timestamp startDate, Timestamp endDate) {
        Page<BookingEntity> bookingEntities = bookingService.getBookings(pageable, bookingNumber, customerFirstName,
                customerLastName, startDate, endDate);
        return bookingEntities.map(bookingResponseMapper::convertToBookingResponse);
    }

    public BigDecimal calculateCost(CostInformation costInformation) {
        return bookingService.calculateTotalCost(costInformation.getBookingNumber(), costInformation.getTip());
    }

    public void sendEmail(EmailInformation emailInformation) {
        bookingService.sendEmail(emailInformation);
    }
}
