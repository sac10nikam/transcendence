package com.nobodyhub.transcendence.api.common;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@ToString
@Data
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
    private String url;

    /**
     * Request body
     */
    private Object body;

    /**
     * topic to cache the response
     */
    private String topic;

    /**
     * Used by Json serializer
     */
    public ApiRequestMessage() {
    }

    public ApiRequestMessage(String url, String topic) {
        this.url = url;
        this.topic = topic;
    }
}
