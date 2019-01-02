package com.nobodyhub.transcendence.zhihu.common.client;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hub-people", path = "/people")
public interface PeopleHubClient {
    @Async
    @PostMapping(path = "/zhihu-member/save/no-return")
    void saveZhihuMemberNoReturn(@RequestBody ZhihuMember zhihuMember);
}
