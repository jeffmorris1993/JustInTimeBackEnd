package com.justintime.cardetail.Application_Service;

import com.justintime.cardetail.Domain_Service.BookingService;
import com.justintime.cardetail.Model.BookingInformation;
import com.justintime.cardetail.Model.Response.BookingResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UpsertBookingService {

    private BookingService bookingService;

    private static final Logger logger = LoggerFactory.getLogger(UpsertBookingService.class);

    public BookingResponse run(BookingInformation bookingInformation){
        logger.info("Starting to create a booking");
        return bookingService.upsertBooking(bookingInformation);
    }
}
