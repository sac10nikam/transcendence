package com.nobodyhub.transcendence.zhihu.client;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hub-people", path = "/people")
public interface PeopleHubClient {
    @PostMapping(path = "/zhihu-member/save/no-return")
    void saveZhihuMemberNoReturn(@RequestBody ZhihuMember zhihuMember);
}
