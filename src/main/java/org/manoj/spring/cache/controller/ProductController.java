package org.manoj.spring.cache.controller;

import org.manoj.spring.cache.entity.Product;
import org.manoj.spring.cache.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {

        logger.info("created new product received with product id {}", product.getProductId());
         productService.createProduct(product);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {

        Product product = productService.getProduct(productId);

        if(null != product){
            logger.info("product details fetched for id {}", productId);
        }

        return ResponseEntity.ok(product);
    }
}