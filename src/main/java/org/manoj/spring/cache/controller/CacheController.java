package org.manoj.spring.cache.controller;

import org.manoj.spring.cache.cache.LRUCache;
import org.manoj.spring.cache.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private final LRUCache<Long, Product> cache;

    public CacheController(LRUCache<Long, Product> cache) {
        this.cache = cache;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("hits", cache.getHitCount());
        stats.put("miss", cache.getMissCount());
        stats.put("evictions", cache.getEvictionCount());
        stats.put("hitRate", cache.getHitRate());
        return stats;
    }
}