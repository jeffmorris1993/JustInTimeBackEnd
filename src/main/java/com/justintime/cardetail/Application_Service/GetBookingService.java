package com.justintime.cardetail.Application_Service;

import com.justintime.cardetail.Domain_Service.BookingService;
import com.justintime.cardetail.Model.BookingResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GetBookingService {

    private BookingService bookingService;

    private static final Logger logger = LoggerFactory.getLogger(GetBookingService.class);

    public List<BookingResponse> run() {
        return bookingService.getBookings();
    }
}
