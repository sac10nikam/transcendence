package com.nobodyhub.transcendence.zhihu.throttle.service;

import com.nobodyhub.transcendence.api.common.message.ApiChannel;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.binder.PollableMessageSource;

/**
 * If the API share the thresholde, should send to the same channel
 */
public interface ZhihuApiChannel extends ApiChannel {
    /**
     * Request for Zhihu Answer
     */
    String ZHIHU_ANSWER_REQUEST_CHANNEL = "zhihu-answer-request-channel";

    @Input(ZHIHU_ANSWER_REQUEST_CHANNEL)
    PollableMessageSource receiveAnswerRequest();

    /**
     * Request for Zhihu Article
     */
    String ZHIHU_ARTICLE_REQUEST_CHANNEL = "zhihu-article-request-channel";

    @Input(ZHIHU_ARTICLE_REQUEST_CHANNEL)
    PollableMessageSource receiveArticleRequest();

    /**
     * Request for Zhihu Column
     */
    String ZHIHU_COLUMN_REQUEST_CHANNEL = "zhihu-column-request-channel";

    @Input(ZHIHU_COLUMN_REQUEST_CHANNEL)
    PollableMessageSource receiveColumnRequest();

    /**
     * Request for Zhihu Comment
     */
    String ZHIHU_COMMENT_REQUEST_CHANNEL = "zhihu-comment-request-channel";

    @Input(ZHIHU_COMMENT_REQUEST_CHANNEL)
    PollableMessageSource receiveCommentRequest();

    /**
     * Request for Zhihu Member
     */
    String ZHIHU_MEMBER_REQUEST_CHANNEL = "zhihu-member-request-channel";

    @Input(ZHIHU_MEMBER_REQUEST_CHANNEL)
    PollableMessageSource receiveMemberRequest();

    /**
     * Request for Zhihu Question
     */
    String ZHIHU_QUESTION_REQUEST_CHANNEL = "zhihu-question-request-channel";

    @Input(ZHIHU_QUESTION_REQUEST_CHANNEL)
    PollableMessageSource receiveQuestionRequest();

    /**
     * Request for Zhihu Topic
     */
    String ZHIHU_TOPIC_REQUEST_CHANNEL = "zhihu-topic-request-channel";

    @Input(ZHIHU_TOPIC_REQUEST_CHANNEL)
    PollableMessageSource receiveTopicRequest();
}
