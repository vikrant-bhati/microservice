package com.springboot.microservices.apigateway.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalFilteringClass implements GlobalFilter {

    //Any request that will be made to ASG will come through GlobalFilteringClass and we will
    //implement simple logging for now
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("URI request made -> {}", exchange.getRequest().getPath());
        //we are simply logging the URI and returning the request we can make further updates also
        // if needed
        return chain.filter(exchange);
    }
}
