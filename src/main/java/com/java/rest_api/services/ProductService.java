package com.java.rest_api.services;

import com.java.rest_api.models.Product;
import com.java.rest_api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String save(Product product) {
        if (product != null) {
            com.java.rest_api.models.db.Product dbProduct = new com.java.rest_api.models.db.Product();
            dbProduct.setProductName(product.getProductName());
            dbProduct.setCurrentInventory(product.getCurrentInventory());
            dbProduct.setProductPrice(product.getProductPrice());

            try {
                com.java.rest_api.models.db.Product savedProduct = productRepository.save(dbProduct);
                return "Product " + savedProduct.getProductName() + " has been saved successfully";
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Product could not be saved due to: " + e.getMessage());
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product could not be saved");
    }

    public com.java.rest_api.models.db.Product update(com.java.rest_api.models.db.Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Product could not be saved due to: " + e.getMessage());
        }
    }

    public List<Product> getProducts() {
        List<com.java.rest_api.models.db.Product> dbProducts =  productRepository.findAll();
        List<Product> products = new ArrayList<>();

        for (com.java.rest_api.models.db.Product dbProduct : dbProducts) {
            Product product = new Product();
            product.setProductName(dbProduct.getProductName());
            product.setCurrentInventory(dbProduct.getCurrentInventory());
            product.setProductPrice(dbProduct.getProductPrice());

            products.add(product);
        }

        return products;
    }

    public com.java.rest_api.models.db.Product getProductByName(String productName) {
        return productRepository.findProductByProductName(productName);
    }

    public String deleteProductByName(String productName) {
        com.java.rest_api.models.db.Product dbProduct = productRepository.findProductByProductName(productName);

        if (dbProduct != null) {
            productRepository.delete(dbProduct);
            return "Product " + dbProduct.getProductName() + " has been deleted successfully";
        } else {
            return "Product " + productName + " could not be found";
        }
    }
}
