package com.nobodyhub.transcendence.zhihu.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ZhihuMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhihuMemberApplication.class, args);
    }
}
