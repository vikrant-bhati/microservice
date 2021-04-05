package com.springboot.microservices.currencyconversionservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CurrencyConversionConfiguration {

    /**
     * We can use two ways to call other service i.e. using rest template and using feign
     * we will use them both
     * @return
     */
    @Bean
    public RestTemplate getTemplate(){
        return new RestTemplate();
    }
}
