package com.nobodyhub.transcendence.zhihu.question.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@ConditionalOnProperty(name = "zhihu.api.version", havingValue = "4")
@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("https://www.zhihu.com/api/v4/").build();
    }
}
