package com.nobodyhub.transcendence.api;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableFeignClients
public class ApiCommonConfiguration {
}
