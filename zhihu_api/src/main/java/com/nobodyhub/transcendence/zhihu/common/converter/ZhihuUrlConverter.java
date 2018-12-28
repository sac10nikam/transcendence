package com.nobodyhub.transcendence.zhihu.common.converter;

import org.springframework.stereotype.Component;

@Component
public class ZhihuUrlConverter {
    /**
     * Convert given URL to a compatible url with current API version
     *
     * @param url
     * @return
     */
    public String convert(String url) {
        if (url == null) {
            return null;
        }
        return url.replaceFirst("^(https://|http://)", "") // remove protocol
            .replaceFirst("^www\\.zhihu\\.com", ""); // remove host
    }
}
