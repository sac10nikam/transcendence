package com.nobodyhub.transcendence.api.common.kafka;

import com.nobodyhub.transcendence.message.ApiRequestMessage;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

public class KafkaMessageHeader<T> {
    /**
     * The original request that get the contents of message
     */
    public static final KafkaMessageHeader<ApiRequestMessage> ORIGIN_REQUEST
        = new KafkaMessageHeader<>("origin-request", ApiRequestMessage.class);

    /**
     * the header of the response
     */
    public static final KafkaMessageHeader<HttpHeaders> RESPONSE_HEADERS
        = new KafkaMessageHeader<>("response-headers", HttpHeaders.class);

    /**
     * key in the message header
     */
    @Getter
    private String key;
    /**
     * source type before serialization
     */
    @Getter
    private Class<T> type;

    private KafkaMessageHeader(String key, Class<T> type) {
        this.key = key;
        this.type = type;
    }
}
