package com.nobodyhub.transcendence.zhihu.topic.controller;

import com.nobodyhub.transcendence.zhihu.topic.message.ZhihuTopicMessager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("topics")
public class ZhihuTopicController {
    private final ZhihuTopicMessager topicMessager;

    public ZhihuTopicController(ZhihuTopicMessager topicMessager) {
        this.topicMessager = topicMessager;
    }

    @GetMapping(path = "/control/refresh")
    @ResponseStatus(HttpStatus.OK)
    void refreshTopics() {
        topicMessager.getTopicCategories();
    }
}
