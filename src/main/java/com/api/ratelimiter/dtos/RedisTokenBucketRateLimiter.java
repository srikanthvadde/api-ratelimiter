package com.api.ratelimiter.dtos;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class RedisTokenBucketRateLimiter {
    private final long capacity = 5;
    private final double refillRatePerSecond = 5.0 / 60.0; 

    @Cacheable(value = "rateLimit", key = "#clientId + ':tokens'")
    public double getTokens(String clientId) {
        return capacity; 
    }

    @Cacheable(value = "rateLimit", key = "#clientId + ':lastRefill'")
    public long getLastRefillTime(String clientId) {
        return System.nanoTime(); 
    }

    @CachePut(value = "rateLimit", key = "#clientId + ':tokens'")
    public double updateTokens(String clientId, double newTokens) {
        return newTokens;
    }

    @CachePut(value = "rateLimit", key = "#clientId + ':lastRefill'")
    public long updateLastRefillTime(String clientId, long now) {
        return now;
    }

    public boolean tryConsume(String clientId) {
        long now = System.nanoTime();
        long lastRefillTime = getLastRefillTime(clientId);
        double elapsedTime = (now - lastRefillTime) / 1_000_000_000.0; 

        double tokensToAdd = elapsedTime * refillRatePerSecond;
        double currentTokens = Math.min(capacity, getTokens(clientId) + tokensToAdd);

        if (currentTokens >= 1) {
            updateTokens(clientId, currentTokens - 1);
            updateLastRefillTime(clientId, now);
            return true; 
        }
        return false;
    }
}
