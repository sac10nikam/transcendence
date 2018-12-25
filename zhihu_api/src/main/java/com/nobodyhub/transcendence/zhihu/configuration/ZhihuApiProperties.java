package com.nobodyhub.transcendence.zhihu.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:zhihu-api.properties")
@ConfigurationProperties("zhihu.api")
@Getter
public class ZhihuApiProperties {
    /**
     * Zhihu Api version
     * e.g., 4 means v4, url starts with https://www.zhihu.com/api/v4
     */
    private Integer version;
    /**
     * The interval between each request in ms
     * e.g., the external API allows 10 req/sec
     * the delay should be set to:
     * 1000 / 10 / (# of instance)
     */
    private Integer delay;
    /**
     * The way to sort the query result from API
     * should be supported by the API
     */
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
