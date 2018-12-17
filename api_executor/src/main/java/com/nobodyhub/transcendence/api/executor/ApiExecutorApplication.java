package com.nobodyhub.transcendence.api.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableBinding
public class ApiExecutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiExecutorApplication.class, args);
    }

}

