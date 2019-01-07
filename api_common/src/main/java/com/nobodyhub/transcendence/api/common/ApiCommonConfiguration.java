package com.nobodyhub.transcendence.api.common;

import com.nobodyhub.transcendence.api.common.cookies.ApiRedisConfiguration;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExceptionHandler;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan
@EnableFeignClients
@EnableAsync
@Import(ApiRedisConfiguration.class)
public class ApiCommonConfiguration implements AsyncConfigurer {

    @Nullable
    public ApiAsyncExceptionHandler getApiAsyncExceptionHandler() {
        return new ApiAsyncExceptionHandler();
    }
}
