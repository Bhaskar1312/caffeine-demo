package com.example.caffeinedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressService.class);

    private CacheManager cacheManager;

    public AddressService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Cacheable(cacheNames = "addresses")
    public String getAddress(long customerId) {
        log.info("Method getAddress is invoked for customer {}", customerId);
        return "123 Main St";
    }

    public String getAddress2(long customerId) {
        if(cacheManager.getCache("addresses2").get(customerId)!= null) {
            return cacheManager.getCache("addresses2").get(customerId).get().toString();
        }
        log.info("Method getAddresses2 is invoked for customer {}", customerId);
        String address = "123 Main St";
        cacheManager.getCache("addresses2").put(customerId, address);
        return address;
    }


}
