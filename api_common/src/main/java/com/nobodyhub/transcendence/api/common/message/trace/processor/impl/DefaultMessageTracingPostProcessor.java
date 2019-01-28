package com.nobodyhub.transcendence.api.common.message.trace.processor.impl;

import com.nobodyhub.transcendence.api.common.message.trace.processor.MessageTracingPostProcessor;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Default message tracing post-processor, invoked after the message is processed.
 */
public class DefaultMessageTracingPostProcessor implements MessageTracingPostProcessor {
    @Override
    public void process(ProceedingJoinPoint joinPoint) {
        //TODO: add post processor
    }
}
