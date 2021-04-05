package com.springboot.microservices.currencyexchangeservice.controller;


import com.springboot.microservices.currencyexchangeservice.entity.CurrencyExchangeEntity;
import com.springboot.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;





@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment env;

    @Autowired
    private CurrencyExchangeRepository repos;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchangeEntity retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
    //    CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50), "Hard Coded");
        String port = env.getProperty("local.server.port");
        CurrencyExchangeEntity currencyExchange = repos.findByFromAndTo(from,to);
        if(currencyExchange==null){
            throw new RuntimeException("Details Not Found");
        }
        currencyExchange.setEnvironment(port);

        return currencyExchange;
    }
}
