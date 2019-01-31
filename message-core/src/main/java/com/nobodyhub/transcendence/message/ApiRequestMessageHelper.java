package com.nobodyhub.transcendence.message;

import org.springframework.http.HttpHeaders;

import java.util.Map;
import java.util.Optional;

/**
 * Helper class to manage the {@link ApiRequestMessage}
 */
public final class ApiRequestMessageHelper {

    /**
     * add cookies to message
     *
     * @param message
     * @param cookies
     */
    public static void addCookies(ApiRequestMessage message, String cookies) {
        message.getHeaders().add(HttpHeaders.COOKIE, cookies);
    }

    /**
     * add header to message
     *
     * @param message
     * @param key
     * @param value
     */
    public static void addHeader(ApiRequestMessage message, String key, String value) {
        message.getHeaders().add(key, value);
    }

    /**
     * add property to message
     *
     * @param message
     * @param key
     * @param value
     */
    public static void addProperty(ApiRequestMessage message, String key, Object value) {
        message.getProperties().put(key, value);
    }

    /**
     * get property of message
     *
     * @param message
     * @param key
     * @param clz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getProperty(ApiRequestMessage message, String key, Class<T> clz) {
        Map<String, Object> properties = message.getProperties();
        Object value = properties.get(key);
        if (value != null && clz.isAssignableFrom(value.getClass())) {
            return Optional.of((T) value);
        }
        return Optional.empty();
    }


    /**
     * The key for property of {@link ApiRequestMessage}.
     * This property is used to invalid the cookies that get from request sent before this one
     */
    private static final String PROP_FLUSH_COOKIES = "$prop-refresh-cookies";

    /**
     * check whether the message will force to refresh the cookies
     *
     * @param message
     * @return
     */
    public static boolean isFlushCookies(ApiRequestMessage message) {
        Optional<Boolean> isRefresh = getProperty(message, PROP_FLUSH_COOKIES, Boolean.class);
        if (isRefresh.isPresent() && isRefresh.get()) {
            return true;
        }
        return false;
    }

    /**
     * set the message property to flush the cookies
     *
     * @param message
     */
    public static void setFlushCookies(ApiRequestMessage message) {
        addProperty(message, PROP_FLUSH_COOKIES, true);
    }
}
