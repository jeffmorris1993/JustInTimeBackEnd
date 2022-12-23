package com.justintime.cardetail.Application_Service;

import com.justintime.cardetail.Domain_Service.BookingService;
import com.justintime.cardetail.Model.BookingInformation;
import com.justintime.cardetail.Model.CostInformation;
import com.justintime.cardetail.Model.Response.BookingResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BookingApplicationService {

    private BookingService bookingService;

    private static final Logger logger = LoggerFactory.getLogger(BookingApplicationService.class);

    public BookingResponse upsertBooking(BookingInformation bookingInformation){
        logger.info("Starting to create a booking");
        return bookingService.upsertBooking(bookingInformation);
    }

    public List<BookingResponse> getBookings() {
        return bookingService.getBookings();
    }

    public BigDecimal calculateCost(CostInformation costInformation) {
        return bookingService.calculateTotalCost(costInformation.getBookingNumber(), costInformation.getTip());
    }
}
