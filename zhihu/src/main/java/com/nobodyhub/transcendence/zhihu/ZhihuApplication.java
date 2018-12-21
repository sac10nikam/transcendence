package com.nobodyhub.transcendence.zhihu;

import com.nobodyhub.transcendence.zhihu.member.message.ZhihuMemberApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;


@EnableBinding({Source.class, ZhihuMemberApi.class})
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class ZhihuApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhihuApplication.class, args);
    }

}

