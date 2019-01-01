package com.nobodyhub.transcendence.hub.deed.client;

import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hub-people", path = "/people")
public interface PeopleHubClient {
    @PostMapping(path = "/zhihu-member/get")
    People getByZhihuMember(@RequestBody ZhihuMember zhihuMember);
}
