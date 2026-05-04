package org.manoj.spring.cache.service;


import org.manoj.spring.cache.entity.Product;
import org.springframework.stereotype.Service;


public interface ProductService {

    public Product createProduct(Product product);

    public Product getProduct(Long productId);
}
