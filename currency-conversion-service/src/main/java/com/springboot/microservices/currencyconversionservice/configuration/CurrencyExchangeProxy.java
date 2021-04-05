package com.springboot.microservices.currencyconversionservice.configuration;


import com.springboot.microservices.currencyconversionservice.Entity.CurrencyConversionEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//While setting up feign client we configured the server in such a way that we
//provide the URL where to redirect
// Bu this will be difficult to manage with many instances of application running on
// different ports
// To overcome this we use Naming server i.e.  Eureka Naming Server



//Commenting below Lined as we will be using Naming server instead of Hard Coded URL
//@FeignClient(name = "currency-exchange",url = "localhost:8000")
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionEntity retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
