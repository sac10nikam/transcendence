package com.nobodyhub.transcendence.api.common.message.trace.config;

import com.nobodyhub.transcendence.api.common.message.trace.aop.MessageTraceAspect;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.TraceTargetExtractorManager;
import org.springframework.context.annotation.Bean;

/**
 * Configuration for message tracing
 */
public class MessageTracingConfiguration {

    @Bean
    public MessageTraceAspect messageTraceAspect() {
        return new MessageTraceAspect();
    }

    @Bean
    public TraceTargetExtractorManager messageExtractorManage() {
        return new TraceTargetExtractorManager();
    }
}
