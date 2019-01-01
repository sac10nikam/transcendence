package com.nobodyhub.transcendence.hub.topic;

import com.nobodyhub.transcendence.hub.configuration.MongoDbConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;


@EnableFeignClients
@EnableEurekaClient
@Import(MongoDbConfiguration.class)
@SpringBootApplication
public class TopicHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopicHubApplication.class, args);
    }

}

