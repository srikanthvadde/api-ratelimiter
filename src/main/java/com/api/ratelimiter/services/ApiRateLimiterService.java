/* package com.api.ratelimiter.services;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.api.ratelimiter.dtos.RedisTokenBucketRateLimiter;

@Service
public class ApiRateLimiterService {
    private final ConcurrentHashMap<String, RedisTokenBucketRateLimiter> buckets = new ConcurrentHashMap<>();
    private final long refillRatePerMinute=5;
    private final long bucketCapacity=5;

    public boolean allowRequest(String clientId){

        RedisTokenBucketRateLimiter bucket = buckets.computeIfAbsent(clientId, k -> new RedisTokenBucketRateLimiter(bucketCapacity, refillRatePerMinute));
        return bucket.tryConsume();
    }

}
 */