package com.nobodyhub.transcendence.api.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ExternalApiRequestExecutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalApiRequestExecutorApplication.class, args);
    }

}

