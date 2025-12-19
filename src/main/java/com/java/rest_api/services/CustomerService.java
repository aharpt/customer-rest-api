package com.java.rest_api.services;

import com.java.rest_api.models.Customer;
import com.java.rest_api.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public com.java.rest_api.models.db.Customer save(Customer customer) {
        com.java.rest_api.models.db.Customer dbCustomer = new com.java.rest_api.models.db.Customer();

        dbCustomer.setFirstName(customer.getFirstName());
        dbCustomer.setLastName(customer.getLastName());
        dbCustomer.setEmail(customer.getEmail());
        dbCustomer.setDeleted(false);

        try {
            return customerRepository.save(dbCustomer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public com.java.rest_api.models.db.Customer delete(com.java.rest_api.models.db.Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public com.java.rest_api.models.db.Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> getCustomers() {
        List<com.java.rest_api.models.db.Customer> dbCustomers = customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();

        for (com.java.rest_api.models.db.Customer dbCustomer : dbCustomers) {
            Customer customer = new Customer();
            customer.setFirstName(dbCustomer.getFirstName());
            customer.setLastName(dbCustomer.getLastName());
            customer.setEmail(dbCustomer.getEmail());
            customer.setDeleted(dbCustomer.getDeleted());

            if (!dbCustomer.getDeleted()) {
                customers.add(customer);
            }
        }

        return customers;
    }

    public Customer getCustomerByEmail(String email) {
        Optional<com.java.rest_api.models.db.Customer> dbCustomer = customerRepository.findAll().stream().filter(c -> c.getEmail().equals(email)).findFirst();

        if (dbCustomer.isPresent() && !dbCustomer.get().getDeleted()) {
            Customer customer = new Customer();

            customer.setFirstName(dbCustomer.get().getFirstName());
            customer.setLastName(dbCustomer.get().getLastName());
            customer.setEmail(dbCustomer.get().getEmail());
            customer.setDeleted(dbCustomer.get().getDeleted());

            return customer;
        } else {
            return null;
        }
    }
}
