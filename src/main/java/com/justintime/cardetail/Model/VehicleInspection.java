package com.justintime.cardetail.Model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInspection {
    private UUID vehicleInspectionId;
    private List<Integer> exterior;
    private List<Integer> interior;
}
