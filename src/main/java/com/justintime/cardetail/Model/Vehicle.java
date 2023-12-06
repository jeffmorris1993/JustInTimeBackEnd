package com.justintime.cardetail.Model;

import lombok.*;

import java.util.List;
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
    private DetailService<Integer> mainService;
    private List<DetailService<String>> addOns;
    private VehicleInspection vehicleInspection;
}
