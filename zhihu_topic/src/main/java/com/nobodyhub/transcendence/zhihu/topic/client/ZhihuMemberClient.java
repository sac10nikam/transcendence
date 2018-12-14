package com.nobodyhub.transcendence.zhihu.topic.client;

import com.nobodyhub.transcendence.zhihu.api.domain.ZhihuApiMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zhihu-member", path = "/members")
public interface ZhihuMemberClient {
    @PostMapping
    ZhihuApiMember save(@RequestBody ZhihuApiMember member);
}
