package com.nobodyhub.transcendence.api.common.message.trace.aop;

import com.nobodyhub.transcendence.api.common.message.trace.config.MessageTracingConfiguration;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * Aspect for message tracing
 */
@Aspect
@Component
@ConditionalOnBean(MessageTracingConfiguration.class)
public class MessageTraceAspect {
    @Around("@annotation(com.nobodyhub.transcendence.api.common.message.trace.MessageTracer)")
    public Object traceMessage(ProceedingJoinPoint joinPoint) throws Throwable {
        // TODO: process begin status
        Object rVal = joinPoint.proceed();
        // TODO: process end status
        return rVal;
    }
}
