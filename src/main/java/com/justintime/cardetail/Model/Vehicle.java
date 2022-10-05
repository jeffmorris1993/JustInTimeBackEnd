package com.justintime.cardetail.Model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private UUID vehicleId;
    private String make;
    private String model;
    private int year;
    private int serviceType;
}
