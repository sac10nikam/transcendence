package com.nobodyhub.transcendence.zhihu.throttle.service;

import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.cookies.ApiCookies;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiChannelBaseService;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(ZhihuApiChannel.class)
public class ZhihuApiThrottleService extends ApiChannelBaseService<ZhihuApiChannel> {
    private final ApiCookies cookies;


    protected ZhihuApiThrottleService(ZhihuApiChannel channel,
                                      ApiResponseConverter converter,
                                      ApiAsyncExecutor apiAsyncExecutor,
                                      KafkaHeaderHandler headerHandler,
                                      PollableMessageSource requestMessageSource,
                                      ApiCookies cookies) {
        super(channel, converter, apiAsyncExecutor, headerHandler, requestMessageSource);
        this.cookies = cookies;
    }

    protected final void makeOutboundRequest(PollableMessageSource requestMessageSource) {
        try {
            requestMessageSource.poll(m -> {
                    ApiRequestMessage message = ((ApiRequestMessage) m.getPayload());
                    apiAsyncExecutor.execRequest(message);
                    // append the latest cookies
                    injectCookies(message);
                },
                new ParameterizedTypeReference<ApiRequestMessage>() {
                });
        } catch (Exception e) {
            log.error("Error happen when polling message.", e);
        }
    }

    @Override
    protected void injectCookies(ApiRequestMessage message) {
        cookies.inject(message);
    }

    @Override
    protected long getDelay() {
        return 0;
    }
}
