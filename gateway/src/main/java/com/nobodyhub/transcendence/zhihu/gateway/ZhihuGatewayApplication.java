package com.nobodyhub.transcendence.zhihu.gateway;

import com.nobodyhub.transcendence.zhihu.gateway.configuration.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class ZhihuGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhihuGatewayApplication.class, args);
    }
}
