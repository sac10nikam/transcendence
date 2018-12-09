package com.nobodyhub.transcendence.zhihu.topic.service;

import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * https://dzone.com/articles/microservices-communication-feign-as-rest-client
 */
@FeignClient(name = "zhihu-member", path = "/members")
public interface ZhihuMemberClient {
    @PostMapping
    ZhihuMember save(@RequestBody ZhihuMember member);
}
