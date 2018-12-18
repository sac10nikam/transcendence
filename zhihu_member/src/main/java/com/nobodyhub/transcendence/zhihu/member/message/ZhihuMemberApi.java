package com.nobodyhub.transcendence.zhihu.member.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ZhihuMemberApi {
    String ZHIHU_MEMBER = "zhihu-member";

    @Input(ZHIHU_MEMBER)
    SubscribableChannel zhihuMembers();
}
