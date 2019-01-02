package com.nobodyhub.transcendence.api.common.message;

import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.core.ParameterizedTypeReference;

@Slf4j
public abstract class ApiChannelBaseService<T extends ApiChannel> {
    protected final T channel;
    protected final ApiResponseConverter converter;
    protected final ApiAsyncExecutor apiAsyncExecutor;
    protected final KafkaHeaderHandler headerHandler;
    protected final PollableMessageSource requestMessageSource;

    protected ApiChannelBaseService(T channel,
                                    ApiResponseConverter converter,
                                    ApiAsyncExecutor apiAsyncExecutor,
                                    KafkaHeaderHandler headerHandler,
                                    PollableMessageSource requestMessageSource) {
        this.channel = channel;
        this.converter = converter;
        this.apiAsyncExecutor = apiAsyncExecutor;
        this.headerHandler = headerHandler;
        this.requestMessageSource = requestMessageSource;
    }

    protected final void makeOutboundRequest() {
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

    /**
     * Inject cookies into the request message
     *
     * @param message request message
     */
    protected abstract void injectCookies(ApiRequestMessage message);

    /**
     * Get the interval in millisecond between each request
     *
     * @return
     */
    protected abstract long getDelay();
}
