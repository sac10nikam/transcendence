package com.nobodyhub.transcendence.zhihu.throttle.service;

import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.cookies.ApiCookies;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiChannelBaseService;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.nobodyhub.transcendence.zhihu.throttle.service.ZhihuApiChannel.*;

@Slf4j
@Service
@EnableBinding(ZhihuApiChannel.class)
public class ZhihuApiThrottleService extends ApiChannelBaseService<ZhihuApiChannel> {
    private final ApiCookies cookies;
    private final PollableMessageSource answerRequestSource;
    private final PollableMessageSource articleRequestSource;
    private final PollableMessageSource columnRequestSource;
    private final PollableMessageSource commentRequestSource;
    private final PollableMessageSource memberRequestSource;
    private final PollableMessageSource questionRequestSource;
    private final PollableMessageSource topicRequestSource;


    protected ZhihuApiThrottleService(ZhihuApiChannel channel,
                                      ApiResponseConverter converter,
                                      ApiAsyncExecutor apiAsyncExecutor,
                                      KafkaHeaderHandler headerHandler,
                                      ApiCookies cookies,
                                      @Qualifier(ZHIHU_ANSWER_REQUEST_CHANNEL) PollableMessageSource answerRequestSource,
                                      @Qualifier(ZHIHU_ARTICLE_REQUEST_CHANNEL) PollableMessageSource articleRequestSource,
                                      @Qualifier(ZHIHU_COLUMN_REQUEST_CHANNEL) PollableMessageSource columnRequestSource,
                                      @Qualifier(ZHIHU_COMMENT_REQUEST_CHANNEL) PollableMessageSource commentRequestSource,
                                      @Qualifier(ZHIHU_MEMBER_REQUEST_CHANNEL) PollableMessageSource memberRequestSource,
                                      @Qualifier(ZHIHU_QUESTION_REQUEST_CHANNEL) PollableMessageSource questionRequestSource,
                                      @Qualifier(ZHIHU_TOPIC_REQUEST_CHANNEL) PollableMessageSource topicRequestSource) {
        super(channel, converter, apiAsyncExecutor, headerHandler);
        this.cookies = cookies;
        this.answerRequestSource = answerRequestSource;
        this.articleRequestSource = articleRequestSource;
        this.columnRequestSource = columnRequestSource;
        this.commentRequestSource = commentRequestSource;
        this.memberRequestSource = memberRequestSource;
        this.questionRequestSource = questionRequestSource;
        this.topicRequestSource = topicRequestSource;
    }

    @Scheduled(fixedDelayString = "${zhihu.api.delay}")
    public void receiveAnswerRequest() {
        makeOutboundRequest(answerRequestSource);
    }

    @Scheduled(fixedDelayString = "${zhihu.api.delay}")
    public void receiveArticleRequest() {
        makeOutboundRequest(articleRequestSource);
    }

    @Scheduled(fixedDelayString = "${zhihu.api.delay}")
    public void receiveColumnRequest() {
        makeOutboundRequest(columnRequestSource);
    }

    @Scheduled(fixedDelayString = "${zhihu.api.delay}")
    public void receiveCommentRequest() {
        makeOutboundRequest(commentRequestSource);
    }

    @Scheduled(fixedDelayString = "${zhihu.api.delay}")
    public void receiveMemberRequest() {
        makeOutboundRequest(memberRequestSource);
    }

    @Scheduled(fixedDelayString = "${zhihu.api.delay}")
    public void receiveQuestionRequest() {
        makeOutboundRequest(questionRequestSource);
    }

    @Scheduled(fixedDelayString = "${zhihu.api.delay}")
    public void receiveTopicRequest() {
        makeOutboundRequest(topicRequestSource);
    }

    protected final void makeOutboundRequest(PollableMessageSource requestMessageSource) {
        try {
            requestMessageSource.poll(m -> {
                    ApiRequestMessage message = ((ApiRequestMessage) m.getPayload());
                    apiAsyncExecutor.execRequest(message);
                    // append the latest cookies
                    cookies.inject(message);
                },
                new ParameterizedTypeReference<ApiRequestMessage>() {
                });
        } catch (Exception e) {
            log.error("Error happen when polling message.", e);
        }
    }
}
