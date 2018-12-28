package com.nobodyhub.transcendence.api.common.message;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;

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
    private HttpHeaders headers = new HttpHeaders();

    /**
     * Request URL
     */
    private String url;

    /**
     * Request body
     */
    private LinkedMultiValueMap<String, Object> body;

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
