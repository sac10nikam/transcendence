package com.nobodyhub.transcendence.zhihu.common.message;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@ToString
@Data
@RequiredArgsConstructor
public class ApiRequestMessage {
    /**
     * Request Method
     */
    private HttpMethod method = HttpMethod.GET;

    /**
     * Request headers
     */
    private MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

    /**
     * Request URL
     */
    private final String url;

    /**
     * Cookies
     */
    private Map<String, String> cookies = Maps.newHashMap();

    /**
     * topic to cache the response
     */
    private final String topic;
}
