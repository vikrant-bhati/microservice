package com.springboot.microservices.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class CircuitBreakerController {

    @GetMapping("/sample-api")
    // @Retry(name = "default")// We have added a retry logic from resilience4j and this will retry the
    // service as per the properties with name property if we use default retry attempts are 3
    @Retry(name = "sample-api", fallbackMethod = "hardCodedResponse") // defined this property in application.properties
    public String sampleApi() {
        // to check the number of request we will be using logger statement
        log.info("Request to Sample API received");
        //Created an dummy service and calling it using rest controller
        // This wont return any response and therefore our application will fail
        ResponseEntity<String> forEntity =
                new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
                        String.class);

        // return "Circuit Breaker sample API";
        return forEntity.getBody();
    }

    //This is fallback method to perform operation after fall back
    //We can create different methods for different type of exception
    public String hardCodedResponse(Exception ex) {
        return "HardCoded Response";
    }

    /**
     * Created this method as we have another annotation @CircuitBreaker which is more powerful than
     * @Retry. Advantage is when having lot of traffic then Circuit breaker can come into 3 states
     * Open Closed and Half Open
      * @return
     */
    @GetMapping("/sample-api-circuit")
    @CircuitBreaker(name = "default",fallbackMethod = "hardCodedResponse")
    public String getSampleApiCircuit(){
        log.info("Request to sample API with Circuit");
        ResponseEntity<String> forEntity =
                new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
                        String.class);
        return forEntity.getBody();
    }

    /**
     * We have a concept of Rate limiter that will allow only certain request in defined period of time
     */
    @GetMapping("/sample-api-rate-limiter")
    @RateLimiter(name="sample-api")
    public String getSampleApiRateLimiter(){
        log.info("Inside Sample Service for Rate Limiter");
        return "Sample API Rate Limiter";
    }

    /**
     * We can configure number of consecutive request coming to our service using @BulkHead
     *
     */
    @Bulkhead(name="default")
    @GetMapping("/sample-api-bulkHead")
    public String getSampleApiBulkHead(){
        log.info("Inside the get Sample API  from Bulk head ");
        return "Sample API from Bulk Head";
    }
}

