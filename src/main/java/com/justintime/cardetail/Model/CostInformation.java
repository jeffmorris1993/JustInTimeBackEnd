package com.justintime.cardetail.Model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CostInformation {
    private UUID bookingNumber;
    private BigDecimal tip;
}
