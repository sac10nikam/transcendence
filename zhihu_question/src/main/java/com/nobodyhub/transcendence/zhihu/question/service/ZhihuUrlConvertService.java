package com.nobodyhub.transcendence.zhihu.question.service;

public interface ZhihuUrlConvertService {
    /**
     * convert the url due to the API version change
     *
     * @param url
     * @return
     */
    String convert(String url);
}
