package com.justintime.cardetail.Model.RequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.justintime.cardetail.Model.Customer;
import com.justintime.cardetail.Model.Vehicle;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookingInformation {
    private UUID bookingNumber;
    private Customer customer;
    private Timestamp dateOfService;
    private Vehicle vehicle;
    @JsonProperty
    private boolean isSubmitted;
}
