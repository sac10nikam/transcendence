package com.nobodyhub.transcendence.zhihu.api.configuration;

import com.nobodyhub.transcendence.zhihu.api.converter.ZhihuUrlConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ZhihuApiConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("https://www.zhihu.com").build();
    }

    @Bean
    @ConditionalOnProperty(name = "zhihu.api.version", havingValue = "4")
    public ZhihuUrlConverter converter() {
        return url -> {
            if (url == null) {
                return null;
            }
            return url.replaceFirst("^(https://|http://)", "") // remove protocol
                .replaceFirst("^www\\.zhihu\\.com", ""); // remove host
        };
    }
}
