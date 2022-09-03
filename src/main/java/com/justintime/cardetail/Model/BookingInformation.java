package com.justintime.cardetail.Model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookingInformation {
    private Customer customer;
    private Timestamp dateOfService;
    private Vehicle vehicle;

}
