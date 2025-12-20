package com.java.rest_api.controllers;

import com.java.rest_api.models.Order;
import com.java.rest_api.models.db.DBCustomer;
import com.java.rest_api.models.db.DBOrder;
import com.java.rest_api.models.db.DBProduct;
import com.java.rest_api.services.CustomerService;
import com.java.rest_api.services.OrderService;
import com.java.rest_api.services.ProductService;
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
@RequestMapping("/api/v1/order")
@Tag(name = "Order Management", description = "APIs for managing orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "Retrieve existing orders", description = "Retrieves all existing orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Order.class))
                            )
                    }),
            @ApiResponse(responseCode = "500",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/getOrders")
    public ResponseEntity<List<Order>> getOrders() {
        try {
            return ResponseEntity.ok(orderService.getOrders());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Retrieve existing orders by customer", description = "Retrieves all existing orders by customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Order.class))
                            )
                    }),
            @ApiResponse(responseCode = "400",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/getOrdersByCustomer")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@RequestParam String email) {
        try {
            DBCustomer customer = customerService.findByEmail(email);
            if (customer == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(orderService.getOrdersByCustomerId(customer.getId()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Retrieve existing orders by product", description = "Retrieves all existing orders by product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Order.class))
                            )
                    }),
            @ApiResponse(responseCode = "400",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/getOrdersByProduct")
    public ResponseEntity<List<Order>> getOrdersByProduct(@RequestParam String productName) {
        try {
            DBProduct product = productService.getProductByName(productName);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(orderService.getOrdersByProductId(product.getId()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Create a new order", description = "Add a new order to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",
                    content = @Content(schema = @Schema(implementation = String.class))),
    })
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Order order) {
        try {
            DBOrder savedOrder = orderService.save(order);
            return ResponseEntity.ok("Order of " + savedOrder.getProductName() + " has been saved successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Order could not be saved due to: " + e.getMessage());
        }
    }
}
