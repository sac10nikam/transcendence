package com.nobodyhub.transcendence.message.tracer.processor;

import org.aspectj.lang.ProceedingJoinPoint;

public interface MessageTracingProcessor {
    /**
     * process the message at given join point
     *
     * @param joinPoint
     */
    void process(ProceedingJoinPoint joinPoint);
}
