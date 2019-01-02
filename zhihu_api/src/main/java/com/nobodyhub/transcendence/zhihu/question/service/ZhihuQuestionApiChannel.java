package com.nobodyhub.transcendence.zhihu.question.service;

import com.nobodyhub.transcendence.api.common.message.ApiChannel;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


public interface ZhihuQuestionApiChannel extends ApiChannel {
    /**
     * Request sent to fetch Member information
     */
    String OUT_ZHIHU_QUESTION_REQUEST = "out-zhihu-question-request";

    @Output(OUT_ZHIHU_QUESTION_REQUEST)
    MessageChannel sendRequest();

    /**
     * Used for throttle for the outbound request
     */
    String ZHIHU_QUESTION_REQUEST_CHANNEL = "zhihu-question-request-channel";

    @Input(ZHIHU_QUESTION_REQUEST_CHANNEL)
    PollableMessageSource receiveRequest();

    /**
     * Response received contains Zhihu question information
     */
    String IN_ZHIHU_QUESTION_CALLBACK_QUESTION = "in-zhihu-question-callback-question";

    @Input(IN_ZHIHU_QUESTION_CALLBACK_QUESTION)
    SubscribableChannel questionCallback();
}
