package com.nobodyhub.transcendence.hub.deed.client;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hub-people", path = "/people")
public interface PeopleHubController {
    @PostMapping(path = "/zhihu-member")
    void saveZhihuMember(@RequestBody ZhihuMember zhihuMember);
}
