package com.nobodyhub.transcendence.hub.topic.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "zhihu-api", path = "/zhihu/column")
public interface ZhihuColumnApiClient {
    @Async
    @GetMapping(path = "/id/{id}")
    void getColumnById(String columnId);
}
