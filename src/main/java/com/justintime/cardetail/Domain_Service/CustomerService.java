package com.justintime.cardetail.Domain_Service;

import com.justintime.cardetail.Model.Customer;
import com.justintime.cardetail.Model.Entity.CustomerEntity;
import com.justintime.cardetail.Repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerEntity upsertCustomer(Customer customer){
        Optional<CustomerEntity> customerEntity = customerRepository.findByEmail(customer.getEmail());
        CustomerEntity.CustomerEntityBuilder customerEntityBuilder =
                customerEntity.isPresent() ? customerEntity.get().toBuilder() : CustomerEntity.builder();
        return customerRepository.save(customerEntityBuilder
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .streetAddress(customer.getStreetAddress())
                .city(customer.getCity())
                .zip(customer.getZipCode())
                .build());
    }
}
