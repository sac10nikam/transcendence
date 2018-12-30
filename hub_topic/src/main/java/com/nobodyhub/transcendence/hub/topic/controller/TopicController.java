package com.nobodyhub.transcendence.hub.topic.controller;

import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Save zhihu topic
     *
     * @param zhihuTopic
     */
    @PostMapping(path = "/zhihu")
    @ResponseStatus(HttpStatus.OK)
    void saveZhihuTopic(ZhihuTopic zhihuTopic) {
        topicService.saveZhihuTopic(zhihuTopic);
    }
}
