package com.nobodyhub.transcendence.zhihu.member.api;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UrlConverter {
    private final RestTemplate restTemplate;

    public UrlConverter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Expand the given URI template with an array of URI variables.
     *
     * @param uriTemplate
     * @param uriVariables
     * @return
     */
    public String convert(String uriTemplate, Object... uriVariables) {
        return this.restTemplate.getUriTemplateHandler().expand(uriTemplate, uriVariables).toString();
    }
}
