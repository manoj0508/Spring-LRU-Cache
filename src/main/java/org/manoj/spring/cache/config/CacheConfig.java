package org.manoj.spring.cache.config;

import org.manoj.spring.cache.cache.LRUCache;
import org.manoj.spring.cache.entity.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public LRUCache<Long, Product> lruCache() {
        return new LRUCache<>(25); // capacity
    }
}