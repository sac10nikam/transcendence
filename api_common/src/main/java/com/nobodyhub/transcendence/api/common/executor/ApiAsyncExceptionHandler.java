package com.nobodyhub.transcendence.api.common.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class ApiAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.error("Faile to call method {}, result in error {}.", method.getName(), objects, throwable);
        log.error("Calling with parameter: ");
        for (Object object : objects) {
            log.error("Parameter value - : {}", object);
        }
    }
}
