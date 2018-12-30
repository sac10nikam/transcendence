package com.nobodyhub.transcendence.hub.topic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class TopicHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopicHubApplication.class, args);
    }

}

