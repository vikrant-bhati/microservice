package com.springboot.microservices.limitsservice.controller;

import com.springboot.microservices.limitsservice.bean.Limits;
import com.springboot.microservices.limitsservice.configuration.LimitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private LimitConfiguration config;

    @GetMapping("/limits")
    public Limits retrieveLimits(){
        Limits limit  = new Limits(config.getMinimum(),config.getMaximum());
        return limit;
    }
}
