package com.nobodyhub.transcendence.zhihu.column.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.common.client.TopicHubClient;
import com.nobodyhub.transcendence.zhihu.common.cookies.ZhihuApiCookies;
import com.nobodyhub.transcendence.zhihu.common.service.ZhihuApiChannelBaseService;
import com.nobodyhub.transcendence.zhihu.configuration.ZhihuApiProperties;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuColumn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nobodyhub.transcendence.zhihu.column.service.ZhihuColumnApiChannel.IN_ZHIHU_COLUMN_CALLBACK_COLUMN;
import static com.nobodyhub.transcendence.zhihu.column.service.ZhihuColumnApiChannel.ZHIHU_COLUMN_REQUEST_CHANNEL;

@Slf4j
@Service
@EnableBinding(ZhihuColumnApiChannel.class)
public class ZhihuColumnApiService extends ZhihuApiChannelBaseService<ZhihuColumnApiChannel> {
    private final TopicHubClient topicHubClient;

    protected ZhihuColumnApiService(ZhihuColumnApiChannel channel,
                                    ApiResponseConverter converter,
                                    ApiAsyncExecutor apiAsyncExecutor,
                                    KafkaHeaderHandler headerHandler,
                                    @Qualifier(ZHIHU_COLUMN_REQUEST_CHANNEL) PollableMessageSource requestMessageSource,
                                    ObjectMapper objectMapper,
                                    ZhihuApiProperties apiProperties,
                                    ZhihuApiCookies cookies,
                                    TopicHubClient topicHubClient) {
        super(channel, converter, apiAsyncExecutor, headerHandler, requestMessageSource, objectMapper, apiProperties, cookies);
        this.topicHubClient = topicHubClient;
    }

    /**
     * Get article of given id
     *
     * @param columnId
     */
    public void getById(String columnId) {
        String urlTemplate = "http://www.zhihu.com/api/v4/columns/{columnId}";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_COLUMN_CALLBACK_COLUMN, urlTemplate, columnId);
        channel.sendRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_COLUMN_CALLBACK_COLUMN)
    public void receiveArticle(@Payload byte[] message,
                               @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
        Optional<ZhihuColumn> column = converter.convert(message, ZhihuColumn.class);
        column.ifPresent(topicHubClient::saveColumnNoReturn);
    }
}
