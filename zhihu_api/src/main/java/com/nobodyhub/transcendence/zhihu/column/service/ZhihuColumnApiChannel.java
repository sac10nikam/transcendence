package com.nobodyhub.transcendence.zhihu.column.service;

import com.nobodyhub.transcendence.api.common.message.ApiChannel;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ZhihuColumnApiChannel extends ApiChannel {
    /**
     * Request sent to fetch Zhihu Column information
     */
    String OUT_ZHIHU_COLUMN_REQUEST = "out-zhihu-column-request";

    @Output(OUT_ZHIHU_COLUMN_REQUEST)
    MessageChannel sendRequest();

    String ZHIHU_COLUMN_REQUEST_CHANNEL = "zhihu-column-request-channel";

    @Input(ZHIHU_COLUMN_REQUEST_CHANNEL)
    PollableMessageSource receiveRequest();

    /**
     * Response received contains column contents
     */
    String IN_ZHIHU_COLUMN_CALLBACK_COLUMN = "in-zhihu-column-callback-column";

    @Input(IN_ZHIHU_COLUMN_CALLBACK_COLUMN)
    SubscribableChannel columnCallback();
}
