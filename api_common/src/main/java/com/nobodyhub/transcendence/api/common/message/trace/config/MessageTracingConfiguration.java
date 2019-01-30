package com.nobodyhub.transcendence.api.common.message.trace.config;

import com.nobodyhub.transcendence.api.common.message.trace.aop.MessageTraceAspect;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.TraceTargetExtractorManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

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

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("javis-server", 6379));
    }

    @Bean
    public StringRedisTemplate redisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }
}
