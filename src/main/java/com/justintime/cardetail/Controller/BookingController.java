package com.justintime.cardetail.Controller;

import com.justintime.cardetail.Application_Service.GetBookingService;
import com.justintime.cardetail.Application_Service.UpsertBookingService;
import com.justintime.cardetail.Model.BookingInformation;
import com.justintime.cardetail.Model.BookingResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@AllArgsConstructor
public class BookingController {

    private final UpsertBookingService upsertBookingService;
    private final GetBookingService getBookingService;

    @PostMapping("/booking")
    public ResponseEntity<UUID> upsertBooking(@RequestBody BookingInformation bookingInformation) {
        return ResponseEntity.ok(upsertBookingService.run(bookingInformation));
    }

    @GetMapping("/booking")
    public ResponseEntity<List<BookingResponse>> getBookings() {
        return ResponseEntity.ok(getBookingService.run());
    }

}
