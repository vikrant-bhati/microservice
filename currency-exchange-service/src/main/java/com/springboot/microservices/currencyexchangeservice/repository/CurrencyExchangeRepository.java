package com.springboot.microservices.currencyexchangeservice.repository;

import com.springboot.microservices.currencyexchangeservice.entity.CurrencyExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository< CurrencyExchangeEntity, Long> {

     CurrencyExchangeEntity findByFromAndTo(String from, String to);
}
