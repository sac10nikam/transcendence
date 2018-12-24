package com.nobodyhub.transcendence.zhihu.question.service.impl;

import com.nobodyhub.transcendence.zhihu.question.service.ZhihuUrlConvertService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@ConditionalOnProperty(name = "zhihu.api.version", havingValue = "4")
@Service
public class ZhihuUrlConvertServiceImplV4 implements ZhihuUrlConvertService {
    @Override
    public String convert(String url) {
        Assert.notNull(url, "The URL to be converted can not be null");
        return url.replaceFirst("^(https://|http://)", "") // remove protocol
            .replaceFirst("^www\\.zhihu\\.com", "") // remove host
            .replaceFirst("^/api/v4", ""); //remove api prefix
    }
}
