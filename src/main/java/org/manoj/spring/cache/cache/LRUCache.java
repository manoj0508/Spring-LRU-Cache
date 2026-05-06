package org.manoj.spring.cache.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCache<K, V> {

    private class Node {
        K key;
        V value;
        Node prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final ConcurrentHashMap<K, Node> map;
    private final ReentrantLock lock = new ReentrantLock();

    private Node head, tail;

    private final AtomicLong hitCount = new AtomicLong();
    private final AtomicLong missCount = new AtomicLong();
    private final AtomicLong evictionCount = new AtomicLong();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new ConcurrentHashMap<>();
    }

    public V get(K key) {
        Node node = map.get(key);

        if (node == null) {
            missCount.incrementAndGet();
            System.out.println("[LRU-CACHE] MISS key=" + key);
            return null;
        }

        lock.lock();
        try {
            moveToHead(node);
        } finally {
            lock.unlock();
        }

        hitCount.incrementAndGet();
        System.out.println("[LRU-CACHE] HIT key=" + key);
        return node.value;
    }

    public void put(K key, V value) {
        lock.lock();
        try {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                node.value = value;
                moveToHead(node);
                return;
            }

            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addToHead(newNode);

            if (map.size() > capacity) {
                Node lru = removeTail();
                map.remove(lru.key);
                evictionCount.incrementAndGet();
                System.out.println("[LRU-CACHE] EVICT key=" + lru.key);
            }

        } finally {
            lock.unlock();
        }
    }


    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    private void addToHead(Node node) {
        node.next = head;
        node.prev = null;

        if (head != null) head.prev = node;
        head = node;

        if (tail == null) tail = node;
    }

    private void removeNode(Node node) {
        if (node.prev != null) node.prev.next = node.next;
        else head = node.next;

        if (node.next != null) node.next.prev = node.prev;
        else tail = node.prev;
    }

    private Node removeTail() {
        Node res = tail;
        removeNode(res);
        return res;
    }


    public long getHitCount() {
        return hitCount.get();
    }

    public long getMissCount() {
        return missCount.get();
    }

    public long getEvictionCount() {
        return evictionCount.get();
    }

    public double getHitRate() {
        long hits = hitCount.get();
        long total = hits + missCount.get();
        return total == 0 ? 0 : (double) hits / total;
    }

    public void printStats() {
        System.out.println("====== LRU CACHE STATS ======");
        System.out.println("Hits       : " + getHitCount());
        System.out.println("Miss       : " + getMissCount());
        System.out.println("Evictions  : " + getEvictionCount());
        System.out.println("Hit Rate   : " + getHitRate());
    }
}