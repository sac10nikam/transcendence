package com.nobodyhub.transcendence.hub.tag.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Client for ZhihuTopicController
 */
@FeignClient(name = "zhihu-api", path = "/zhihu/topics")
public interface ZhihuTopicApiClient {
    @Async
    @GetMapping(path = "/topicId/{topicId}")
    void getTopicById(@PathVariable("topicId") String topicId);

    @Async
    @GetMapping(path = "/topicId/{topicId}/parents")
    void getTopicParents(@PathVariable("topicId") String topicId);

    @Async
    @GetMapping(path = "/topicId/{topicId}/children")
    void getTopicChildren(@PathVariable("topicId") String topicId);
}
