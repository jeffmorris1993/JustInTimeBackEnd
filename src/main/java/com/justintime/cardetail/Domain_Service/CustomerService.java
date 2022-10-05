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

    public CustomerEntity createCustomer(Customer customer){
        return customerRepository.save(CustomerEntity.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .streetAddress(customer.getStreetAddress())
                .city(customer.getCity())
                .zip(customer.getZipCode())
                .build());
    }

    public CustomerEntity updateCustomer(Customer customer){
        Optional<CustomerEntity> customerEntity = customerRepository.findById(customer.getCustomerId());
        return customerEntity.map(c -> customerRepository.save(c.toBuilder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .streetAddress(customer.getStreetAddress())
                .city(customer.getCity())
                .zip(customer.getZipCode())
                .build()
        )).orElse(null);
    }
}
