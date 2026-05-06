package org.manoj.spring.cache.service;

import org.manoj.spring.cache.cache.LRUCache;
import org.manoj.spring.cache.entity.Product;
import org.manoj.spring.cache.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final LRUCache<Long, Product> cache;

    public ProductServiceImpl(ProductRepository productRepository, LRUCache<Long, Product> cache) {
        this.productRepository = productRepository;
        this.cache = cache;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(Long productId) {

        long start = System.currentTimeMillis();

        Product cachedProduct = cache.get(productId);

        if (cachedProduct != null) {

            long end = System.currentTimeMillis();

            System.out.println("API Response Time (CACHE): "
                    + (end - start) + " ms");

            return cachedProduct;
        }

        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        cache.put(productId, product);

        long end = System.currentTimeMillis();

        System.out.println("API Response Time (DB): "
                + (end - start) + " ms");

        return product;
    }
}