package com.nobodyhub.transcendence.zhihu.common.converter;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ZhihuUrlConverter {
    private final RestTemplate restTemplate;

    public ZhihuUrlConverter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
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

    /**
     * Expand the given URI template with an array of URI variables.
     *
     * @param uriTemplate
     * @param uriVariables
     * @return
     */
    public String expand(String uriTemplate, Object... uriVariables) {
        return this.restTemplate.getUriTemplateHandler().expand(uriTemplate, uriVariables).toString();
    }
}
