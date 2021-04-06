package com.springboot.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
    /**
     * This is used to build the routes to different URLS
     * We can create custom URLS, change existing route, change header, change body, etc
     *
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator getRouter(RouteLocatorBuilder builder) {
        // Created routes using route builder
        return builder.routes()
                // we are building our first route
                .route(
                        // now if request come which as path with get
                        p -> p.path("/get")
                                // redirect it to give dummy path, we can pass any URL here
                                .uri("http://httpbin.org:80")
                )

                //similarly we can add something to add header
                .route(
                        p -> p.path("/getHeader")
                                // we sill use filter to update the request
                                .filters(f -> f.addRequestHeader("headerName", "headerValue"))
                                .uri("http://httpbin.org:80")
                )
                //similarly we can add something to add to request
                .route(
                        p -> p.path("/getParam")
                                // we sill use filter to update the request
                                .filters(f -> f.addRequestParameter("parameterName", "parameterValue"))
                                .uri("http://httpbin.org:80")
                )

                //Now we will implement these thing in out application URL also

                .route(
                        //request that starts with currency-exchange
                        p -> p.path("/currency-exchange/**")
                                // we sill use filter redirect to lb i.e. load balancer and then the naming server
                                // and redirect it to the registered name
                                // Note: we would want to disable the locator gateway property
                                .uri("lb://currency-exchange")
                )
                .route(
                        //request that starts with currency-conversion
                        p -> p.path("/currency-conversion/**")
                                // we sill use filter redirect to lb i.e. load balancer and then the naming server
                                // and redirect it to the registered name
                                // Note: we would want to disable the locator gateway property
                                .uri("lb://currency-conversion")
                )
                .route(
                        //request that starts with currency-conversion-feign
                        p -> p.path("/currency-conversion-feign/**")
                                // we sill use filter redirect to lb i.e. load balancer and then the naming server
                                // and redirect it to the registered name
                                // Note: we would want to disable the locator gateway property
                                .uri("lb://currency-conversion")
                )
                .route(
                        //Just for fun I created another route for fun
                        //	http://localhost:8765/random-url/from/USD/to/INR/quantity/10 -> redirect to
                        //	http://localhost:8765/currency-conversion/from/USD/to/INR/quantity/10
                        p -> p.path("/random-url/**")
                                .filters(
                                        f -> f.rewritePath("/random-url/(?<segment>.*)", "/currency-conversion/${segment}")
                                )
                                .uri("lb://currency-conversion")
                )
                .build();
    }
}
