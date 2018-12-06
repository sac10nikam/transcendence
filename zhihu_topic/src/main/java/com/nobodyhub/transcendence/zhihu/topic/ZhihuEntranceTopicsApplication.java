package com.nobodyhub.transcendence.zhihu.topic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ZhihuEntranceTopicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhihuEntranceTopicsApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("https://www.zhihu.com").build();
    }
}
