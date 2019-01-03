package com.nobodyhub.transcendence.hub.topic.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zhihu-api", path = "/zhihu/topics")
public interface ZhihuQuestionApiClient {
    @Async
    @GetMapping("/id/{id}")
    void getQuestionById(@PathVariable("id") String id);
}
