package com.nobodyhub.transcendence.zhihu.api.common.service;

import java.util.Optional;

public interface ApiRequestService {
    /**
     * Send get request to fetch data
     *
     * @param url
     * @param clz
     * @param urlVariables
     * @param <T>
     * @return
     */
    <T> Optional<T> doGet(String url, Class<T> clz, Object... urlVariables);
}
