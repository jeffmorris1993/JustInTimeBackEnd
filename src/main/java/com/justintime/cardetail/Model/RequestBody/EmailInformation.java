package com.justintime.cardetail.Model.RequestBody;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailInformation {
    private UUID bookingNumber;
    private List<String> serviceProviders;
    private String notes;
}
