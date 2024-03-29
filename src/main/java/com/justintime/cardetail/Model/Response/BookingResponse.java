package com.justintime.cardetail.Model.Response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private UUID bookingNumber;
    private boolean isSubmitted;
    private UUID customerId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String zip;
    private DetailServiceResponse detailServiceResponse;
    private List<AddOnResponse> addOnResponses;
    private String dateOfService;
    private UUID vehicleId;
    private int year;
    private String make;
    private String model;
    private List<String> exteriorInspection;
    private List<String> interiorInspection;
    private BigDecimal baseCost;
    private BigDecimal totalCost;
    private BigDecimal tip;
    private String notes;
    private String serviceProviders;
}

