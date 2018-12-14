package com.nobodyhub.transcendence.api.executor.client;

import com.nobodyhub.transcendence.api.executor.client.domain.ZhihuApiMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zhihu-member", path = "/members")
public interface ZhihuMemberClient {
    @PostMapping(name = "save")
    ZhihuApiMember save(@RequestBody ZhihuApiMember member);
}
