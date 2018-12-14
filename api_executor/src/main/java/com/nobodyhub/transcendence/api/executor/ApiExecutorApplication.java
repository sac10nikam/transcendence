package com.nobodyhub.transcendence.api.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ApiExecutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiExecutorApplication.class, args);
    }

}

