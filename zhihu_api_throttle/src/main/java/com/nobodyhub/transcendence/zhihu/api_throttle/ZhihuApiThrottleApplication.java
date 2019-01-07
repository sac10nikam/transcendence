package com.nobodyhub.transcendence.zhihu.api_throttle;

import com.nobodyhub.transcendence.api.common.ApiCommonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableEurekaClient
@SpringBootApplication
@Import(ApiCommonConfiguration.class)
public class ZhihuApiThrottleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhihuApiThrottleApplication.class, args);
    }

}

