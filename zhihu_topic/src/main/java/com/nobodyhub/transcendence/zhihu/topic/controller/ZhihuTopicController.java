package com.nobodyhub.transcendence.zhihu.topic.controller;

import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.topic.service.ZhihuTopicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("topics")
public class ZhihuTopicController {
    private final ZhihuTopicService topicService;

    public ZhihuTopicController(ZhihuTopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping(path = "/id/{id}")
    ZhihuTopic getTopicById(@PathVariable("id") String id) {
        return topicService.getById(id).orElse(new ZhihuTopic());
    }

    @GetMapping(path = "/name/{name}")
    List<ZhihuTopic> getTopicByName(@PathVariable("name") String name) {
        return topicService.getByName(name);
    }

    @PostMapping
    ZhihuTopic save(@RequestBody ZhihuTopic topic) {
        return this.topicService.save(topic);
    }
}
