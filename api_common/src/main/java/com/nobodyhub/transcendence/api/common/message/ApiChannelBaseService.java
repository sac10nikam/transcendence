package com.nobodyhub.transcendence.api.common.message;

import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ApiChannelBaseService<T extends ApiChannel> {
    protected final T channel;
    protected final ApiResponseConverter converter;
    protected final ApiAsyncExecutor apiAsyncExecutor;
    protected final KafkaHeaderHandler headerHandler;

    protected ApiChannelBaseService(T channel,
                                    ApiResponseConverter converter,
                                    ApiAsyncExecutor apiAsyncExecutor,
                                    KafkaHeaderHandler headerHandler) {
        this.channel = channel;
        this.converter = converter;
        this.apiAsyncExecutor = apiAsyncExecutor;
        this.headerHandler = headerHandler;
    }
}
