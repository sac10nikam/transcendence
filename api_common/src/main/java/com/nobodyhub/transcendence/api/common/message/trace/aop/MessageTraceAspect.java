package com.nobodyhub.transcendence.api.common.message.trace.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Aspect for message tracing
 */
@Aspect
public class MessageTraceAspect {
    @Around("@annotation(com.nobodyhub.transcendence.api.common.message.trace.MessageTracer)")
    public Object traceMessage(ProceedingJoinPoint joinPoint) throws Throwable {
        // TODO: process begin status
        Object rVal = joinPoint.proceed();
        // TODO: process end status
        return rVal;
    }
}
