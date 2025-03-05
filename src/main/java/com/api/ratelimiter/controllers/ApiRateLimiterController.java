package com.api.ratelimiter.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/api-ratelimiter")
public class ApiRateLimiterController {


    @GetMapping("/request")
    public String handleRequest() {
        return "Request successful!";
        
    }


}
