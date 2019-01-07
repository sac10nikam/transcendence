package com.nobodyhub.transcendence.api.common.cookies;

import com.google.common.base.Joiner;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ApiCookies {
    private static final String HASH_KEY = "API_COOKIES";
    /**
     * Use redis to store cookies, map from topic to cookie values
     */
    private final StringRedisTemplate redisTemplate;
    private final KafkaHeaderHandler headerHandler;

    public ApiCookies(StringRedisTemplate redisTemplate,
                      KafkaHeaderHandler headerHandler) {
        this.redisTemplate = redisTemplate;
        this.headerHandler = headerHandler;
    }

    /**
     * Update cookies using contents of message header
     *
     * @param messageHeaders
     */
    public void extract(@NotNull MessageHeaders messageHeaders) {
        Optional<ApiRequestMessage> requestMessage = headerHandler.getOriginRequest(messageHeaders);
        if (requestMessage.isPresent()) {
            Optional<HttpHeaders> responseHeaders = headerHandler.getResponseHeader(messageHeaders);
            if (responseHeaders.isPresent()) {
                List<String> newCookies = responseHeaders.get().get(HttpHeaders.SET_COOKIE);
                if (newCookies != null) {
                    redisTemplate.boundHashOps(HASH_KEY).put(
                        requestMessage.get().getTopic(),
                        Joiner.on("; ").join(newCookies)
                    );
                }
            }
        }
    }

    /**
     * inject corresponding cookies to the requst message
     *
     * @param message the request messgage
     */
    public void inject(@NotNull ApiRequestMessage message) {
        String cookie = redisTemplate.<String, String>boundHashOps(HASH_KEY).get(message.getTopic());
        if (cookie != null) {
            message.addCookies(cookie);
        }
    }
}
