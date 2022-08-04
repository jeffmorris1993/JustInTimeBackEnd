package com.justintime.cardetail.Model;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private String make;
    private String model;
    private int year;
    private int serviceType;
}
