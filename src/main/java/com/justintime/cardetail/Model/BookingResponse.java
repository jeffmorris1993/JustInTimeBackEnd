package com.justintime.cardetail.Model;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private UUID bookingNumber;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String zip;
    private int serviceType;
    private String dateOfService;
    private int year;
    private String make;
    private String model;
}
