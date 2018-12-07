package com.nobodyhub.transcendence.fetcher.service;

public interface ZhihuUrlConvertService {
    /**
     * convert the url due to the API version change
     *
     * @param url
     * @return
     */
    String convert(String url);
}
