package com.nobodyhub.transcendence.hub.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Client for ZhihuTopicController admin functions
 */
@FeignClient(name = "zhihu-api", path = "/zhihu/topics")
public interface ZhihuTopicApiClient {
    @Async
    @GetMapping(path = "/admin/refresh")
    @ResponseStatus(HttpStatus.OK)
    void refreshTopics(@RequestParam(value = "fetchTopics", defaultValue = "false") boolean fetchTopics);
}
