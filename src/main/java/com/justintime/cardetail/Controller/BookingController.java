package com.justintime.cardetail.Controller;

import com.justintime.cardetail.Application_Service.BookingApplicationService;
import com.justintime.cardetail.Model.RequestBody.BookingInformation;
import com.justintime.cardetail.Model.RequestBody.CostInformation;
import com.justintime.cardetail.Model.RequestBody.EmailInformation;
import com.justintime.cardetail.Model.Response.BookingResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingApplicationService bookingApplicationService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/booking")
    public ResponseEntity<BookingResponse> upsertBooking(@RequestBody BookingInformation bookingInformation) {
        if (bookingInformation == null) {
            throw new IllegalArgumentException("Booking information is required");        }
        try {
            return ResponseEntity.ok(bookingApplicationService.upsertBooking(bookingInformation));
        } catch (Exception e) {
            throw new RuntimeException("Internal Server Error: " + e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/booking")
    public ResponseEntity<Page<BookingResponse>> getBookings(Pageable pageable,
                                                             @RequestParam(required = false) UUID bookingNumber,
                                                             @RequestParam(required = false) String firstName,
                                                             @RequestParam(required = false) String lastName,
                                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate,
                                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDate) {
        try {
            Timestamp startTimestamp = startDate != null ? Timestamp.from(startDate.toInstant()) : null;
            Timestamp endTimestamp = endDate != null ? Timestamp.from(endDate.toInstant()) : null;
            return ResponseEntity.ok(bookingApplicationService.getBookings(pageable, bookingNumber, firstName, lastName,
                    startTimestamp, endTimestamp));
        } catch (Exception e) {
            throw new RuntimeException("Internal Server Error: " + e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/totalCost")
    public ResponseEntity<BigDecimal> calculateTotalCost(@RequestBody CostInformation costInformation) {
        try {
            return ResponseEntity.ok(bookingApplicationService.calculateCost(costInformation));
        } catch (Exception e) {
            throw new RuntimeException("Internal Server Error: " + e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailInformation emailInformation) {
        try {
            bookingApplicationService.sendEmail(emailInformation);
            return ResponseEntity.ok("okay");
        } catch (Exception e) {
            throw new RuntimeException("Internal Server Error: " + e.getMessage());
        }
    }

}
