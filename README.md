# 🚀 Spring Boot LRU Cache API

A high-performance **Spring Boot REST API** with a **custom thread-safe LRU (Least Recently Used) cache** implementation, designed to optimize database access and improve response time.

---

## 📌 Project Overview

This project demonstrates how to:

* Build a REST API using Spring Boot
* Implement a **custom in-memory LRU cache**
* Make the cache **thread-safe** for concurrent requests
* Track **cache performance metrics (hit/miss/evictions)**
* Reduce database load and improve latency

---

## ⚡ Key Features

### 🧠 Custom LRU Cache

* Built from scratch using:

    * `ConcurrentHashMap` for O(1) lookup
    * Doubly Linked List for LRU ordering
* Ensures **O(1)** time complexity for `get` and `put`

---

### 🔒 Thread-Safe Design

* Uses `ReentrantLock` to handle concurrent modifications
* Safe for multi-threaded environments (real-world API traffic)

---

### 📊 Cache Metrics

Tracks:

* ✅ Cache Hits
* ❌ Cache Misses
* 🔁 Evictions
* 📈 Hit Rate

Exposed via REST API:

```http
GET /api/cache/stats
```

---

### ⚡ Performance Optimization

* Cache HIT → ~0 ms response time
* Cache MISS → DB call (~5 ms)
* Significant reduction in database load

---

## 🏗️ Tech Stack

* Java 25
* Spring Boot 3.x
* Spring Web (REST APIs)
* Spring Data JPA
* MySQL
* Maven

---

## 📂 API Endpoints

### 📦 Get Product

```http
GET /api/products/{productId}
```

#### Behavior:

* Returns product from **cache if available**
* Falls back to **database on cache miss**

---

### ➕ Create Product

```http
POST /api/products
```

#### Sample Request:

```json
{
  "productId": 1001,
  "productName": "iPhone 15",
  "productDesc": "Apple smartphone with A16 chip"
}
```

---

### 📊 Cache Stats

```http
GET /api/cache/stats
```

#### Sample Response:

```json
{
  "hits": 5,
  "miss": 2,
  "evictions": 1,
  "hitRate": 0.71
}
```

---

## 🧪 Sample Logs

```text
[LRU-CACHE] MISS key=1003

API Response Time (DB): 50 ms
[spring-api-cache] [nio-8080-exec-2] o.m.s.c.controller.ProductController     : product details fetched for id 1003
[LRU-CACHE] MISS key=1002

API Response Time (DB): 6 ms
[spring-api-cache] [nio-8080-exec-3] o.m.s.c.controller.ProductController     : product details fetched for id 1002
[LRU-CACHE] HIT key=1003

API Response Time (CACHE): 0 ms
 [spring-api-cache] [nio-8080-exec-4] o.m.s.c.controller.ProductController     : product details fetched for id 1003
[LRU-CACHE] HIT key=1002
API Response Time (CACHE): 0 ms

[spring-api-cache] [nio-8080-exec-5] o.m.s.c.controller.ProductController     : product details fetched for id 1002
[LRU-CACHE] MISS key=1001

API Response Time (DB): 6 ms

[spring-api-cache] [nio-8080-exec-6] o.m.s.c.controller.ProductController     : product details fetched for id 1001
[LRU-CACHE] HIT key=1002
API Response Time (CACHE): 0 ms
2026
```

---

## 🧠 System Design Highlights

* O(1) cache operations
* Efficient memory usage with LRU eviction
* Thread-safe implementation for concurrent access
* Clear separation of concerns (Controller → Service → Cache)

---

## 🚀 How to Run

```bash
git clone https://github.com/manoj0508/Spring-LRU-Cache.git
cd Spring-LRU-Cache
mvn clean install
mvn spring-boot:run
```

---

## 🔮 Future Enhancements

* ⏳ Add TTL (Time-based expiration)
* 📦 Integrate Redis (Distributed Cache)
* ⚡ Compare with Caffeine cache
* 📊 Integrate Micrometer + Prometheus
* 🔄 Add cache warm-up strategy

---

## 💡 Why This Project Matters

This project demonstrates:

* Strong understanding of **data structures (LRU)**
* Ability to design **high-performance backend systems**
* Hands-on experience with **concurrency & optimization**
* Practical implementation of **system design concepts**

---

## 👨‍💻 Author

**Manoj Kumar**

---

## ⭐ If you found this useful, give it a star!
