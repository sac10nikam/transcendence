package com.nobodyhub.transcendence.hub.people;

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
public class PeopleHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeopleHubApplication.class, args);
    }

}

