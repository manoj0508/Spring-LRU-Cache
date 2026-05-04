package org.manoj.spring.cache.repository;

import org.manoj.spring.cache.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByProductId(Long productId);
}
