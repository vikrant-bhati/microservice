package com.springboot.microservices.currencyconversionservice.controller;

import com.springboot.microservices.currencyconversionservice.Entity.CurrencyConversionEntity;
import com.springboot.microservices.currencyconversionservice.configuration.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;


@RestController
public class CurrencyConversionController {
    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @Autowired
    private RestTemplate template;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionEntity calculateCurrencyConversionRestTemplate(@PathVariable String to, @PathVariable String from, @PathVariable BigDecimal quantity) {
        HashMap<String, String> map = new HashMap<>();
        map.put("from", from);
        map.put("to", to);
        ResponseEntity<CurrencyConversionEntity> currencyExchangeResponse = template.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionEntity.class, map);

        CurrencyConversionEntity currencyConversion = currencyExchangeResponse.getBody();

        BigDecimal product = quantity.multiply(currencyConversion.getConversionMultiple());
        return new CurrencyConversionEntity(currencyConversion.getId(), from, to, currencyConversion.getConversionMultiple(), quantity, product, currencyConversion.getEnvironment()+" from Rest Template");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionEntity calculateCurrencyConversionFeign(@PathVariable String to, @PathVariable String from, @PathVariable BigDecimal quantity) {

        CurrencyConversionEntity currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from,to);
        BigDecimal product = quantity.multiply(currencyConversion.getConversionMultiple());
        return new CurrencyConversionEntity(currencyConversion.getId(), from, to, currencyConversion.getConversionMultiple(), quantity, product, currencyConversion.getEnvironment()+ " from feign proxy");
    }
}
