package com.nobodyhub.transcendence.api.executor.cookies;

import com.google.common.base.Joiner;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Component
public class ApiCookies {
    private static final String HASH_KEY = "API_COOKIES";
    /**
     * Use redis to store cookies, map from topic to cookie values
     */
    private final StringRedisTemplate redisTemplate;

    public ApiCookies(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Update cookies from Http header and stop for topic
     *
     * @param httpHeaders
     * @param topic
     */
    public void extract(@NotNull HttpHeaders httpHeaders, String topic) {
        List<String> newCookies = httpHeaders.get(HttpHeaders.SET_COOKIE);
        if (newCookies != null) {
            redisTemplate.boundHashOps(HASH_KEY).put(
                topic,
                Joiner.on("; ").join(newCookies)
            );
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
            ApiRequestMessageHelper.addCookies(message, cookie);
        }
    }
}
