package com.nobodyhub.transcendence.zhihu.comment.service;

import com.nobodyhub.transcendence.api.common.message.ApiChannel;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ZhihuCommentApiChannel extends ApiChannel {
    /**
     * Request sent to fetch Zhihu Comment information
     */
    String OUT_ZHIHU_COMMENT_REQUEST = "out-zhihu-comment-request";

    @Output(OUT_ZHIHU_COMMENT_REQUEST)
    MessageChannel sendRequest();

    String ZHIHU_COMMENT_REQUEST_CHANNEL = "zhihu-comment-request-channel";

    @Input(ZHIHU_COMMENT_REQUEST_CHANNEL)
    PollableMessageSource receiveRequest();

    /**
     * Response received contains answer comments
     */
    String IN_ZHIHU_COMMENT_CALLBACK_ANSWER_COMMENT = "in-zhihu-comment-callback-answer-comment";

    @Input(IN_ZHIHU_COMMENT_CALLBACK_ANSWER_COMMENT)
    SubscribableChannel answerCommentCallback();

    /**
     * Response received contains answer comments
     */
    String IN_ZHIHU_COMMENT_CALLBACK_ARTICLE_COMMENT = "in-zhihu-comment-callback-article-comment";

    @Input(IN_ZHIHU_COMMENT_CALLBACK_ARTICLE_COMMENT)
    SubscribableChannel articleCommentCallback();

    /**
     * Response received contains answer comments
     */
    String IN_ZHIHU_COMMENT_CALLBACK_QUESTION_COMMENT = "in-zhihu-comment-callback-question-comment";

    @Input(IN_ZHIHU_COMMENT_CALLBACK_QUESTION_COMMENT)
    SubscribableChannel questionCommentCallback();

}
