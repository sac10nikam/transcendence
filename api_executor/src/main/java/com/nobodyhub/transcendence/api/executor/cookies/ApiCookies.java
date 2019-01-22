package com.nobodyhub.transcendence.api.executor.cookies;

import com.google.common.collect.Lists;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ApiCookies {
    /**
     * A timstamp of Long type.
     * if not null, indicate that the only request sent after that timestamp is able to update the cookies from response
     */
    private static final String VALID_FROM = "valid-from";


    /**
     * Use redis to store cookies, map from topic to cookie values
     */
    private final StringRedisTemplate redisTemplate;

    public ApiCookies(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Update cookies from Http header
     * the cache key will be the host of the request URL
     *
     * @param httpHeaders
     * @param requestMessage
     */
    public void extract(@NotNull HttpHeaders httpHeaders, ApiRequestMessage requestMessage, long requestAt) {
        //use the domain as the key
        String key = getHashKey(requestMessage);
        Optional<Long> validFrom = getValidFrom(key);
        boolean validFromExist = validFrom.isPresent();
        boolean requestAfterValidFrom = validFrom.isPresent() && (validFrom.get() - requestAt < 0);
        if (validFromExist && requestAfterValidFrom) {
            parseCookiesFromHeaders(httpHeaders).forEach(cookie -> {
                redisTemplate.boundHashOps(key).put(
                    cookie.getName(),
                    cookie.getValue()
                );
                if (cookie.getMaxAge() > 0) {
                    redisTemplate.boundHashOps(key).expire(cookie.getMaxAge(), TimeUnit.MILLISECONDS);
                }
            });
        }

        //check whether to update the cookies
        if (ApiRequestMessageHelper.isFlushCookies(requestMessage)) {
            setValidFrom(key);
        }
    }

    /**
     * inject corresponding cookies to the requst message
     *
     * @param message the request messgage
     */
    public void inject(@NotNull ApiRequestMessage message) {
        String key = getHashKey(message);
        Map<String, String> cookies = redisTemplate.<String, String>opsForHash().entries(key);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> cookie : cookies.entrySet()) {
            sb.append(cookie.getKey()).append("=\"").append(cookie.getValue()).append("\";");
        }
        // delete last <code>;</code>
        sb.deleteCharAt(sb.length() - 1);
        ApiRequestMessageHelper.addCookies(message, sb.toString());
    }

    /**
     * parse cookies from http headeers
     *
     * @param httpHeaders
     * @return
     */
    private List<HttpCookie> parseCookiesFromHeaders(HttpHeaders httpHeaders) {
        List<HttpCookie> cookies = Lists.newArrayList();
        List<String> cookieHeaders = httpHeaders.get(HttpHeaders.SET_COOKIE);
        if (cookieHeaders != null) {
            cookieHeaders.forEach(header -> cookies.addAll(HttpCookie.parse(header)));
        }

        cookieHeaders = httpHeaders.get(HttpHeaders.SET_COOKIE2);
        if (cookieHeaders != null) {
            cookieHeaders.forEach(header -> cookies.addAll(HttpCookie.parse(header)));
        }
        return cookies;
    }

    /**
     * Return the valid from timestamp for cookies
     * Cookies will be updated when either:
     * 1. valid from is null, or
     * 2. System.currentTimeMillis() is after valid from timestamp
     *
     * @param key
     * @return
     */
    private Optional<Long> getValidFrom(String key) {
        Object validFrom = redisTemplate.<String, String>boundHashOps(key).get(VALID_FROM);
        try {
            return Optional.of(Long.valueOf((String) validFrom));
        } catch (NumberFormatException e) {
            log.error("Fail to parse String[{}] into Long", validFrom);
            //invalid long value
            redisTemplate.boundHashOps(key).delete(VALID_FROM);

        }
        return Optional.empty();
    }

    /**
     * Set the value of valid from on Redis server
     *
     * @param key
     */
    private void setValidFrom(String key) {
        redisTemplate.<String, String>boundHashOps(key).put(VALID_FROM, String.valueOf(System.currentTimeMillis()));
    }


    /**
     * get the key for hash map on redis server
     *
     * @param requestMessage
     * @return
     */
    private String getHashKey(ApiRequestMessage requestMessage) {
        try {
            URL url = new URL(requestMessage.getUrlTemplate());
            //use the domain as the key
            return url.getHost();
        } catch (MalformedURLException e) {
            //ignore url parse error
        }
        return "unknown";
    }
}
