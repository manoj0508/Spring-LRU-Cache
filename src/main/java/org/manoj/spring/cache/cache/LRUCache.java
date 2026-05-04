package org.manoj.spring.cache.cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

    private final int capacity;

    private final Map<K, CacheNode<K, V>> cacheMap;

    private final CacheNode<K, V> head;
    private final CacheNode<K, V> tail;

    public LRUCache(int capacity) {

        this.capacity = capacity;
        this.cacheMap = new HashMap<>();

        head = new CacheNode<>(null, null);
        tail = new CacheNode<>(null, null);

        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {

        CacheNode<K, V> node = cacheMap.get(key);

        if (node == null) {
            System.out.println("CACHE MISS : " + key);
            return null;
        }

        moveToFront(node);

        System.out.println("CACHE HIT : " + key);

        return node.value;
    }

    public void put(K key, V value) {

        CacheNode<K, V> node = cacheMap.get(key);

        if (node != null) {

            node.value = value;
            moveToFront(node);

            return;
        }

        if (cacheMap.size() >= capacity) {

            CacheNode<K, V> lruNode = removeLast();

            if (lruNode != null) {
                cacheMap.remove(lruNode.key);

                System.out.println("CACHE EVICTED : " + lruNode.key);
            }
        }

        CacheNode<K, V> newNode = new CacheNode<>(key, value);

        cacheMap.put(key, newNode);

        addToFront(newNode);
    }

    private void moveToFront(CacheNode<K, V> node) {

        removeNode(node);

        addToFront(node);
    }

    private void addToFront(CacheNode<K, V> node) {

        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(CacheNode<K, V> node) {

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private CacheNode<K, V> removeLast() {

        if (tail.prev == head) {
            return null;
        }

        CacheNode<K, V> lastNode = tail.prev;

        removeNode(lastNode);

        return lastNode;
    }
}