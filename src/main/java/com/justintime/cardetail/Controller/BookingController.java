package com.justintime.cardetail.Controller;

import com.justintime.cardetail.Application_Service.BookingApplicationService;
import com.justintime.cardetail.Model.RequestBody.BookingInformation;
import com.justintime.cardetail.Model.RequestBody.CostInformation;
import com.justintime.cardetail.Model.RequestBody.EmailInformation;
import com.justintime.cardetail.Model.Response.BookingResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingApplicationService bookingApplicationService;

    @PostMapping("/booking")
    public ResponseEntity<BookingResponse> upsertBooking(@RequestBody BookingInformation bookingInformation) {
        return ResponseEntity.ok(bookingApplicationService.upsertBooking(bookingInformation));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/booking")
    public ResponseEntity<List<BookingResponse>> getBookings() {
        return ResponseEntity.ok(bookingApplicationService.getBookings());
    }

    @PostMapping("/totalCost")
    public ResponseEntity<BigDecimal> calculateTotalCost(@RequestBody CostInformation costInformation) {
        return ResponseEntity.ok(bookingApplicationService.calculateCost(costInformation));
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailInformation emailInformation) {
        bookingApplicationService.sendEmail(emailInformation);
        return ResponseEntity.ok("okay");
    }

}
