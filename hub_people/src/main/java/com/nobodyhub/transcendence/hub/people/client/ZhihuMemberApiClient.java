package com.nobodyhub.transcendence.hub.people.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zhihu-api", path = "/zhihu/members")
public interface ZhihuMemberApiClient {

    @Async
    @GetMapping(path = "/urlToken/{urlToken}")
    void getByMemberUrlToken(@PathVariable("urlToken") String urlToken);
}
