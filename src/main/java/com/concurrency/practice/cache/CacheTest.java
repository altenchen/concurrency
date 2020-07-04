package com.concurrency.practice.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author altenchen
 * @time 2020/7/2
 * @description 功能
 */
@Slf4j
public class CacheTest {
    
    public static void main(String[] args) {
        Cache<Integer, String> cacheMap = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .concurrencyLevel(5)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build();
        
        cacheMap.put(1, "1");
    
        System.out.println("cacheValue before sleep -> " + cacheMap.getIfPresent(1));
    
        try {
            Thread.sleep(6000);
    
            System.out.println("cacheValue after sleep -> " + cacheMap.getIfPresent(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
