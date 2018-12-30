package com.nobodyhub.transcendence.zhihu.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.common.cookies.ZhihuApiCookies;
import com.nobodyhub.transcendence.zhihu.configuration.ZhihuApiProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ApiChannelBaseService<T extends ApiChannel> {
    protected final T channel;
    protected final ApiResponseConverter converter;
    protected final ObjectMapper objectMapper;
    protected final ZhihuApiProperties apiProperties;
    protected final ApiAsyncExecutor apiAsyncExecutor;
    protected final ZhihuApiCookies cookies;
    protected final KafkaHeaderHandler headerHandler;

    protected ApiChannelBaseService(T channel,
                                    ApiResponseConverter converter,
                                    ObjectMapper objectMapper,
                                    ZhihuApiProperties apiProperties,
                                    ApiAsyncExecutor apiAsyncExecutor,
                                    ZhihuApiCookies cookies,
                                    KafkaHeaderHandler headerHandler) {
        this.channel = channel;
        this.converter = converter;
        this.objectMapper = objectMapper;
        this.apiProperties = apiProperties;
        this.apiAsyncExecutor = apiAsyncExecutor;
        this.cookies = cookies;
        this.headerHandler = headerHandler;
    }

    protected void makeOutboundRequest(ApiRequestMessage message) throws InterruptedException {
        apiAsyncExecutor.execRequest(message);
        // append the latest cookies
        cookies.inject(message);
        try {
            Thread.sleep(apiProperties.getDelay());
        } catch (InterruptedException e) {
            log.warn("Sleep interrupted by {}.", e);
            throw e;
        }
    }
}
