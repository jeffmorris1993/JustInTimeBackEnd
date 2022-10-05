package com.justintime.cardetail.Model;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String streetAddress;
    private String city;
    private String zipCode;

}
