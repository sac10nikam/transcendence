package com.nobodyhub.transcendence.api.common.message;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.UUID;

@ToString
public class ApiRequestMessage {
    /**
     * unique id of the message
     */
    @Getter
    private final String uid = UUID.randomUUID().toString();

    /**
     * status of the message
     */
    @Getter
    @Setter
    private MessageStatus status = MessageStatus.NEW;

    /**
     * Request Method
     */
    @Getter
    @Setter
    private HttpMethod method = HttpMethod.GET;

    /**
     * Request headers
     */
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    /**
     * Request URL template
     */
    @Getter
    private String urlTemplate;

    /**
     * Request URL variables
     */
    @Getter
    private String[] urlVariables;

    /**
     * Request body
     */
    @Getter
    @Setter
    private LinkedMultiValueMap<String, Object> body;

    /**
     * channel to cache the response
     */
    @Getter
    private String topic;

    /**
     * the properties of this request messages
     * <p>
     * can be used to pass information from sender to receiver
     */
    @Getter
    private Map<String, Object> properties = Maps.newHashMap();

    /**
     * Used by Json serializer
     */
    public ApiRequestMessage() {
    }

    public ApiRequestMessage(String topic, String urlTemplate, String... urlVariables) {
        this.topic = topic;
        try {
            this.urlTemplate = URLDecoder.decode(urlTemplate, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            this.urlTemplate = urlTemplate;
        }
        this.urlVariables = urlVariables;
    }
}
