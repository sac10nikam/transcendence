package com.nobodyhub.transcendence.api.common.message.trace.processor.impl;

import com.nobodyhub.transcendence.api.common.message.trace.processor.MessageTracingPreProcessor;
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
