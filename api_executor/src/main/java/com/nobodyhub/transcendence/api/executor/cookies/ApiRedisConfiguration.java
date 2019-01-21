package com.nobodyhub.transcendence.api.executor.cookies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class ApiRedisConfiguration {
    /**
     * TODO: remove hard-coded redis server and handle redis server in a more decent way
     *
     * @return
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("javis-server", 6379));
    }

    @Bean
    public StringRedisTemplate redisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }
}
