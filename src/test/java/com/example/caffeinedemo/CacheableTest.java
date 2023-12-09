package com.example.caffeinedemo;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
public class CacheableTest {
    public record Customer(String id, String name) {
        // equals, hashCode- Should I override?

        }

    final static AtomicInteger cacheableCalled = new AtomicInteger();
    final static AtomicInteger cachePutCalled = new AtomicInteger();

    public static class CustomerCachedService {

        @Cacheable("CustomerCache")
        public Customer cacheable(String v) {
            cacheableCalled.incrementAndGet();
            return new Customer(v, "Cacheable "+v);
        }

        @CachePut("CustomerCache")
        public Customer cachePut(String b) {
            cachePutCalled.incrementAndGet();
            return new Customer(b, "CachePut "+b);
        }
    }

    @Configuration
    @EnableCaching
    public static class CacheConfigurations {

        @Bean
        public CustomerCachedService CustomerCachedService() {
            return new CustomerCachedService();
        }

        @Bean
        public CacheManager cacheManager() {
            return new CaffeineCacheManager("CustomerCache");
        }
    }

    @Autowired
    private CustomerCachedService cachedService;

    @Test
    public void testCacheable() {
        for (int i = 0; i < 1000; i++) {
            cachedService.cacheable("A");
        }
        Assertions.assertEquals(cacheableCalled.get(), 1);
    }

    @Test
    public void testCachePut() {
        for (int i = 0; i < 100; i++) {
            cachedService.cachePut("B");
        }
        Assertions.assertEquals(cachePutCalled.get(), 100);
    }
}

