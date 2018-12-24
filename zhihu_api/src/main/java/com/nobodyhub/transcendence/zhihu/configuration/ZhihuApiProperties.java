package com.nobodyhub.transcendence.zhihu.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:zhihu-api.properties")
@ConfigurationProperties("zhihu.api")
@Getter
public class ZhihuApiProperties {
    private Integer version;
    private Integer limit;
    private SortBy sortBy;

    public enum SortBy {
        DEFAULT("default"),
        CREATED("created");

        @Getter
        private String raw;

        SortBy(String raw) {
            this.raw = raw;
        }
    }
}
