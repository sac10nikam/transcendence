package com.nobodyhub.transcendence.zhihu.question.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.common.client.TopicHubClient;
import com.nobodyhub.transcendence.zhihu.common.cookies.ZhihuApiCookies;
import com.nobodyhub.transcendence.zhihu.common.service.ApiChannelBaseService;
import com.nobodyhub.transcendence.zhihu.configuration.ZhihuApiProperties;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nobodyhub.transcendence.zhihu.question.service.ZhihuQuestionApiChannel.IN_ZHIHU_QUESTION_CALLBACK_QUESTION;
import static com.nobodyhub.transcendence.zhihu.question.service.ZhihuQuestionApiChannel.ZHIHU_QUESTION_REQUEST_CHANNEL;

@Slf4j
@Service
@EnableBinding(ZhihuQuestionApiChannel.class)
public class ZhihuQuestionApiService extends ApiChannelBaseService<ZhihuQuestionApiChannel> {
    private final TopicHubClient topicHubClient;

    protected ZhihuQuestionApiService(ZhihuQuestionApiChannel channel,
                                      ApiResponseConverter converter,
                                      ObjectMapper objectMapper,
                                      ZhihuApiProperties apiProperties,
                                      ApiAsyncExecutor apiAsyncExecutor,
                                      ZhihuApiCookies cookies,
                                      KafkaHeaderHandler headerHandler,
                                      TopicHubClient topicHubClient) {
        super(channel, converter, objectMapper, apiProperties, apiAsyncExecutor, cookies, headerHandler);
        this.topicHubClient = topicHubClient;
    }

    /**
     * Retrieve message from queue of outbound request
     *
     * @param message contents of request
     * @throws InterruptedException
     */
    @StreamListener(ZHIHU_QUESTION_REQUEST_CHANNEL)
    public void receiveRequest(ApiRequestMessage message) throws InterruptedException {
        makeOutboundRequest(message);
    }

    public void getQuestion(String questionId) {
        String url = "https://www.zhihu.com/api/v4/questions/{questionId}";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_QUESTION_CALLBACK_QUESTION, url, questionId);
        channel.sendRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_QUESTION_CALLBACK_QUESTION)
    public void receiveQuestion(@Payload byte[] message,
                                @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
        Optional<ZhihuQuestion> question = converter.convert(message, ZhihuQuestion.class);
        question.ifPresent(topicHubClient::saveZhihuQuestionNoReturn);
    }
}
