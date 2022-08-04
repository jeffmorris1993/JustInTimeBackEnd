package com.justintime.cardetail.Domain_Service;

import com.justintime.cardetail.Model.Customer;
import com.justintime.cardetail.Model.Entity.CustomerEntity;
import com.justintime.cardetail.Repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerEntity createCustomer(Customer customer){
        return customerRepository.save(CustomerEntity.builder().firstName(customer.getFirstName())
                .lastName(customer.getLastName()).email(customer.getEmail()).streetAddress(customer.getStreetAddress())
                .city(customer.getCity()).zip(customer.getZipCode()).build());
    }
}
