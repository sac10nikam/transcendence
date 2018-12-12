package com.nobodyhub.transcendence.zhihu.api.common.configuration.converter;

public interface ZhihuUrlConverter {
    /**
     * Convert given URL to a compatible url with current API version
     *
     * @param url
     * @return
     */
    String convert(String url);
}
