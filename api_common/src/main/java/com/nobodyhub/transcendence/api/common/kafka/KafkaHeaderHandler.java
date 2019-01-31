package com.nobodyhub.transcendence.api.common.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.nobodyhub.transcendence.message.ApiRequestMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.nobodyhub.transcendence.api.common.kafka.KafkaMessageHeader.ORIGIN_REQUEST;
import static com.nobodyhub.transcendence.api.common.kafka.KafkaMessageHeader.RESPONSE_HEADERS;

@Slf4j
@Component
public class KafkaHeaderHandler {
    private final ObjectMapper objectMapper;

    public KafkaHeaderHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * convenient method for get {@link KafkaMessageHeader#ORIGIN_REQUEST} in the header
     *
     * @param messageHeaders
     * @return
     */
    public Optional<ApiRequestMessage> getOriginRequest(@NotNull MessageHeaders messageHeaders) {
        return get(messageHeaders, ORIGIN_REQUEST);
    }

    /**
     * convenient method for get {@link KafkaMessageHeader#RESPONSE_HEADERS} in the header
     *
     * @param messageHeaders
     * @return
     */
    public Optional<HttpHeaders> getResponseHeader(@NotNull MessageHeaders messageHeaders) {
        return get(messageHeaders, RESPONSE_HEADERS);
    }

    /**
     * Parse content from message header to given type
     *
     * @param messageHeaders kafka message headers
     * @param header         the key to be parse in the header
     * @param <T>
     * @return
     */
    public <T> Optional<T> get(@NotNull MessageHeaders messageHeaders, @NotNull KafkaMessageHeader<T> header) {
        Assert.notNull(messageHeaders, "Message headers can not be null!");
        Assert.notNull(header, "The header can not be null!");
        String headerStr = messageHeaders.get(header.getKey(), String.class);
        if (headerStr != null) {
            try {
                return Optional.of(objectMapper.readValue(headerStr, header.getType()));
            } catch (IOException e) {
                log.error("Fail to deserialize {} into {}.", headerStr, header.getType().getName());
            }
        }
        return Optional.empty();
    }

    public KafkaHeaderBuilder builder() {
        return new KafkaHeaderBuilder(this.objectMapper);
    }

    @RequiredArgsConstructor
    public static class KafkaHeaderBuilder {
        private final Map<String, Object> headers = Maps.newHashMap();
        private final ObjectMapper objectMapper;

        public KafkaHeaderBuilder put(@NotNull String key, Object object) {
            try {
                headers.put(key, objectMapper.writeValueAsString(object));
            } catch (JsonProcessingException e) {
                log.error("Fail to serialize {}.", object);
            }
            return this;
        }

        public MessageHeaders build() {
            return new MessageHeaders(headers);
        }
    }


}
