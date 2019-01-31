package com.nobodyhub.transcendence.message.tracer.processor.impl;

import com.nobodyhub.transcendence.message.tracer.processor.MessageTracingPreProcessor;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Default message tracing pre-processor, invoked before the message is processed.
 * <p>
 * By default doing nothing
 */
public class DefaultMessageTracingPreProcessor implements MessageTracingPreProcessor {
    @Override
    public void process(ProceedingJoinPoint joinPoint) {
        //do nothing
    }
}
