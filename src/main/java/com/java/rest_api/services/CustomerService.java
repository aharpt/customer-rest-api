package com.java.rest_api.services;

import com.java.rest_api.models.Customer;
import com.java.rest_api.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public com.java.rest_api.models.db.Customer save(Customer customer) {
        com.java.rest_api.models.db.Customer dbCustomer = new com.java.rest_api.models.db.Customer();

        dbCustomer.setIndex("12345");
        dbCustomer.setFirstName(customer.getFirstName());
        dbCustomer.setLastName(customer.getLastName());
        dbCustomer.setEmail(customer.getEmail());

        try {
            return customerRepository.save(dbCustomer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
