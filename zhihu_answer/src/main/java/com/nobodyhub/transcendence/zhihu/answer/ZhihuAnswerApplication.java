package com.nobodyhub.transcendence.zhihu.answer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ZhihuAnswerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhihuAnswerApplication.class, args);
    }
}
