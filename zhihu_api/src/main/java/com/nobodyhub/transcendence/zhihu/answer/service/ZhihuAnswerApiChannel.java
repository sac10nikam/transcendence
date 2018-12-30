package com.nobodyhub.transcendence.zhihu.answer.service;

import com.nobodyhub.transcendence.zhihu.common.service.ApiChannel;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ZhihuAnswerApiChannel extends ApiChannel {
    /**
     * Request sent to fetch Zhihu Answer information
     */
    String OUT_ZHIHU_ANSWER_REQUEST = "out-zhihu-answer-request";

    @Output(OUT_ZHIHU_ANSWER_REQUEST)
    MessageChannel sendAnswerRequest();

    String ZHIHU_ANSWER_REQUEST_CHANNEL = "zhihu-answer-request-channel";

    @Input(ZHIHU_ANSWER_REQUEST_CHANNEL)
    SubscribableChannel receiveAnswerRequest();

    /**
     * Response received contains answer contents
     */
    String IN_ZHIHU_ANSWER_CALLBACK_ANSWER = "in-zhihu-answer-callback-answer";

    @Input(IN_ZHIHU_ANSWER_CALLBACK_ANSWER)
    SubscribableChannel answerCallbackForAnswer();

    /**
     * Response received contains answer comments
     */
    String IN_ZHIHU_ANSWER_CALLBACK_COMMENT = "in-zhihu-answer-callback-comment";

    @Input(IN_ZHIHU_ANSWER_CALLBACK_COMMENT)
    SubscribableChannel answerCallbackForComment();
}
