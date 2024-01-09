package com.justintime.cardetail.Model.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder(toBuilder = true)
public class AddOnResponse {
    String addOn;
    BigDecimal addOnCost;
}
