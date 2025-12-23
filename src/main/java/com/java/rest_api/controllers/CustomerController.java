package com.java.rest_api.controllers;

import com.java.rest_api.models.Customer;
import com.java.rest_api.models.db.DBCustomer;
import com.java.rest_api.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@Tag(name = "Customer Management", description = "APIs for managing customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Create a new customer", description = "Add a new customer to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",
                    content = @Content(schema = @Schema(implementation = String.class))),
    })
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Customer customer) {
        try {
            DBCustomer savedCustomer = customerService.save(customer);
            return ResponseEntity.ok("Customer " + savedCustomer.getFirstName() + " " + savedCustomer.getLastName() + " has been saved successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Customer record could not be saved due to: " + e.getMessage());
        }
    }

    @Operation(summary = "Retrieve existing customers", description = "Retrieves all existing customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Customer.class))
                            )
                    })
    })
    @GetMapping("/getCustomers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @Operation(summary = "Retrieve active customers", description = "Retrieves all existing active customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Customer.class))
                            )
                    })
    })
    @GetMapping("/getActiveCustomers")
    public ResponseEntity<List<Customer>> getActiveCustomers() {
        return ResponseEntity.ok(customerService.getActiveCustomers());
    }

    @Operation(summary = "Retrieve customer by email", description = "Retrieves existing customer by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/getCustomerByEmail")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        Customer customer = customerService.getCustomerByEmail(email);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(customer);
        }
    }

    @Operation(summary = "Delete existing customer", description = "Deletes existing customer by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("/deleteCustomerByEmail")
    public ResponseEntity<DBCustomer> deleteCustomerByEmail(@RequestParam String email) {
        try {
            DBCustomer customer = customerService.findByEmail(email);

            if (customer == null) {
                return ResponseEntity.notFound().build();
            }
            customer.setDeleted(true);

            DBCustomer savedCustomer = customerService.delete(customer);
            return ResponseEntity.ok(savedCustomer);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
