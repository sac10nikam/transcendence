package com.nobodyhub.transcendence.zhihu.member;

import com.nobodyhub.transcendence.mongodb.domain.configuration.MongoDbConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Import;

@EnableBinding(Source.class)
@EnableEurekaClient
@SpringBootApplication
@Import(MongoDbConfiguration.class)
public class ZhihuMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhihuMemberApplication.class, args);
    }
}
