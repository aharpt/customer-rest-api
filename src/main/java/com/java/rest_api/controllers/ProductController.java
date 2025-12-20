package com.java.rest_api.controllers;

import com.java.rest_api.models.Product;
import com.java.rest_api.models.db.DBProduct;
import com.java.rest_api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

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

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.save(product));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

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

    @PostMapping("/deleteProductByName")
    public ResponseEntity<String> deleteProductByName(@RequestParam String productName) {
        try {
            return ResponseEntity.ok(productService.deleteProductByName(productName));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
