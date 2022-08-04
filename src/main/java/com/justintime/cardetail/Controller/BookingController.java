package com.justintime.cardetail.Controller;

import com.justintime.cardetail.Application_Service.CreateBookingService;
import com.justintime.cardetail.Model.BookingInformation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookingController {

    private final CreateBookingService createBookingService;

    @PostMapping("/booking")
    public void createBooking(@RequestBody BookingInformation bookingInformation){
        createBookingService.run(bookingInformation);
    }

}
