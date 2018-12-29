package com.nobodyhub.transcendence.zhihu.common.cookies;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.nobodyhub.transcendence.api.common.kafka.KafkaMessageHeader.ORIGIN_REQUEST;
import static com.nobodyhub.transcendence.api.common.kafka.KafkaMessageHeader.RESPONSE_HEADERS;

@Slf4j
@Component
public class ZhihuApiCookies {
    /**
     * Cookies cache, map from topic to cookie values
     */
    private final Map<String, String> cookies = Maps.newConcurrentMap();
    private final KafkaHeaderHandler headerHandler;

    public ZhihuApiCookies(KafkaHeaderHandler headerHandler) {
        this.headerHandler = headerHandler;
    }

    public void update(MessageHeaders messageHeaders) {
        Optional<ApiRequestMessage> requestMessage = headerHandler.get(messageHeaders, ORIGIN_REQUEST);
        if (requestMessage.isPresent()) {
            Optional<HttpHeaders> responseHeaders = headerHandler.get(messageHeaders, RESPONSE_HEADERS);
            if (responseHeaders.isPresent()) {
                List<String> newCookies = responseHeaders.get().get(HttpHeaders.SET_COOKIE);
                if (newCookies != null) {
                    this.cookies.put(requestMessage.get().getTopic(), Joiner.on("; ").join(newCookies));
                }
            }
        }
    }

    public void inject(@NotNull ApiRequestMessage message) {
        String cookie = cookies.get(message.getTopic());
        if (cookie != null) {
            message.addCookies(cookie);
        }
    }
}
