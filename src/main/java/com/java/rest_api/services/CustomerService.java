package com.java.rest_api.services;

import com.java.rest_api.models.Customer;
import com.java.rest_api.models.db.DBCustomer;
import com.java.rest_api.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public DBCustomer save(Customer customer) {
        DBCustomer dbCustomer = new DBCustomer();
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

    public DBCustomer delete(DBCustomer customer) {
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public DBCustomer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> getCustomers() {
        List<DBCustomer> dbCustomers = customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();

        for (DBCustomer dbCustomer : dbCustomers) {
            Customer customer = new Customer();
            customer.setId(dbCustomer.getId());
            customer.setFirstName(dbCustomer.getFirstName());
            customer.setLastName(dbCustomer.getLastName());
            customer.setEmail(dbCustomer.getEmail());
            customer.setDeleted(dbCustomer.getDeleted());
            customers.add(customer);
        }

        return customers;
    }

    public List<Customer> getActiveCustomers() {
        List<DBCustomer> dbCustomers = customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();

        for (DBCustomer dbCustomer : dbCustomers) {
            Customer customer = new Customer();
            customer.setId(dbCustomer.getId());
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
        DBCustomer dbCustomer = customerRepository.findByEmail(email);

        if (dbCustomer != null) {
            Customer customer = new Customer();
            customer.setId(dbCustomer.getId());
            customer.setFirstName(dbCustomer.getFirstName());
            customer.setLastName(dbCustomer.getLastName());
            customer.setEmail(dbCustomer.getEmail());
            customer.setDeleted(dbCustomer.getDeleted());

            return customer;
        } else {
            return null;
        }
    }
}
