package com.nobodyhub.transcendence.zhihu.member.service;

import com.nobodyhub.transcendence.api.common.message.ApiChannel;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ZhihuMemberApiChannel extends ApiChannel {
    /**
     * Request sent to fetch Member information
     */
    String OUT_ZHIHU_MEMBER_REQUEST = "out-zhihu-member-request";

    @Output(OUT_ZHIHU_MEMBER_REQUEST)
    MessageChannel sendMemberRequest();

    String ZHIHU_MEMBER_REQUEST_CHANNEL = "zhihu-member-request-channel";

    @Input(ZHIHU_MEMBER_REQUEST_CHANNEL)
    PollableMessageSource receiveMemberRequest();

    /**
     * Response received contains Zhihu member information
     */
    String IN_ZHIHU_MEMBER_CALLBACK_MEMBER = "in-zhihu-member-callback-member";

    @Input(IN_ZHIHU_MEMBER_CALLBACK_MEMBER)
    SubscribableChannel memberCallbackMember();
}
