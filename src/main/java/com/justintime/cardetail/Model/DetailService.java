package com.justintime.cardetail.Model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DetailService<T> {
    private T id;
    private BigDecimal price;
}
