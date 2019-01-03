package com.nobodyhub.transcendence.hub.deed.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zhihu-api", path = "/zhihu/answer")
public interface ZhihuAnswerApiClient {
    @Async
    @GetMapping(path = "/answer/{answerId}")
    void getAnswerById(@PathVariable("answerId") String answerId);

    @Async
    @GetMapping(path = "/comment/{commentId}")
    void getAnswerComments(@PathVariable("commentId") String commentId);
}
