package com.api.ratelimiter.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.api.ratelimiter.dtos.RedisTokenBucketRateLimiter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitInterceptor implements HandlerInterceptor{

    private final RedisTokenBucketRateLimiter rateLimiter;
    public RateLimitInterceptor(RedisTokenBucketRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String clientId = request.getRemoteAddr(); 
        System.out.println(clientId);
        if(!rateLimiter.tryConsume(clientId)){
            response.setStatus(429); 
            response.getWriter().write("Rate limit exceeded. Try again later.");
            return false;
           }
      return true;
    }



}

