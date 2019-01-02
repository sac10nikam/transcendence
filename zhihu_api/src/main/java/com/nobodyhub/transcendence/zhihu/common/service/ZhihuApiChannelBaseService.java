package com.nobodyhub.transcendence.zhihu.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiChannel;
import com.nobodyhub.transcendence.api.common.message.ApiChannelBaseService;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.common.cookies.ZhihuApiCookies;
import com.nobodyhub.transcendence.zhihu.configuration.ZhihuApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public abstract class ZhihuApiChannelBaseService<T extends ApiChannel> extends ApiChannelBaseService<T> {
    protected final ObjectMapper objectMapper;
    protected final ZhihuApiProperties apiProperties;
    protected final ZhihuApiCookies cookies;

    protected ZhihuApiChannelBaseService(T channel,
                                         ApiResponseConverter converter,
                                         ApiAsyncExecutor apiAsyncExecutor,
                                         KafkaHeaderHandler headerHandler,
                                         PollableMessageSource requestMessageSource,
                                         ObjectMapper objectMapper,
                                         ZhihuApiProperties apiProperties,
                                         ZhihuApiCookies cookies) {
        super(channel, converter, apiAsyncExecutor, headerHandler, requestMessageSource);
        this.objectMapper = objectMapper;
        this.apiProperties = apiProperties;
        this.cookies = cookies;
    }

    /**
     * Retrieve message from queue of outbound request
     */
    @Scheduled(fixedDelayString = "${zhihu.api.delay}")
    public void receiveRequest() {
        makeOutboundRequest();
    }

    @Override
    protected void injectCookies(ApiRequestMessage message) {
        cookies.inject(message);
    }

    @Override
    protected long getDelay() {
        return apiProperties.getDelay();
    }
}
