package com.java.rest_api.controllers;

import com.java.rest_api.models.Product;
import com.java.rest_api.models.db.DBProduct;
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
@RequestMapping("/api/v1/product")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Retrieve existing products", description = "Retrieves all existing products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Product.class))
                            )
                    })
    })
    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @Operation(summary = "Retrieve existing product by name", description = "Retrieves existing product by product name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/getProductByName")
    public ResponseEntity<Product> getProductByName(@RequestParam String productName) {
        DBProduct dbProduct = productService.getProductByName(productName);

        if (dbProduct != null) {
            Product product = new Product();
            product.setProductName(dbProduct.getProductName());
            product.setCurrentInventory(dbProduct.getCurrentInventory());
            product.setProductPrice(dbProduct.getProductPrice());

            return ResponseEntity.ok(product);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create a new product", description = "Add a new product to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",
                    content = @Content(schema = @Schema(implementation = String.class))),
    })
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.save(product));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Update an existing product", description = "Update an existing product by product name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DBProduct.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(schema = @Schema())),
    })
    @PostMapping("/updateProductByName")
    public ResponseEntity<DBProduct> updateProductByName(@RequestParam String productName, @RequestBody Product product) {
        try {
            DBProduct dbProduct = productService.getProductByName(productName);

            if (dbProduct != null) {
                dbProduct.setCurrentInventory(product.getCurrentInventory());
                dbProduct.setProductPrice(product.getProductPrice());

                DBProduct savedProduct = productService.update(dbProduct);

                return ResponseEntity.ok(savedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Delete an existing product", description = "Delete an existing product by product name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",
                    content = @Content(schema = @Schema(implementation = String.class))),
    })
    @PostMapping("/deleteProductByName")
    public ResponseEntity<String> deleteProductByName(@RequestParam String productName) {
        try {
            return ResponseEntity.ok(productService.deleteProductByName(productName));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
