package com.nobodyhub.transcendence.message.tracer.processor.impl;

import com.nobodyhub.transcendence.message.tracer.processor.MessageTracingPostProcessor;
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
