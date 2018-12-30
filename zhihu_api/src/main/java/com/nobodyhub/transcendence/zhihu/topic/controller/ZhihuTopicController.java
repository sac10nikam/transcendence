package com.nobodyhub.transcendence.zhihu.topic.controller;

import com.nobodyhub.transcendence.zhihu.topic.message.ZhihuTopicMessager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zhihu/topics")
public class ZhihuTopicController {
    private final ZhihuTopicMessager topicMessager;

    public ZhihuTopicController(ZhihuTopicMessager topicMessager) {
        this.topicMessager = topicMessager;
    }

    /**
     * Get topic of given id
     *
     * @param topicId
     */
    @GetMapping(path = "/topicId/{topicId}")
    @ResponseStatus(HttpStatus.OK)
    void getTopicById(@PathVariable("topicId") String topicId) {
        topicMessager.getTopicById(topicId);
    }

    /**
     * Get parents topic of topic with given id
     *
     * @param topicId
     */
    @GetMapping(path = "/topicId/{topicId}/parents")
    @ResponseStatus(HttpStatus.OK)
    void getTopicParents(@PathVariable("topicId") String topicId) {
        topicMessager.getTopicParents(topicId);
    }

    /**
     * Get children topic of topic with given id
     *
     * @param topicId
     */
    @GetMapping(path = "/topicId/{topicId}/children")
    @ResponseStatus(HttpStatus.OK)
    void getTopicChildren(@PathVariable("topicId") String topicId) {
        topicMessager.getTopicChildrent(topicId);
    }

    /**
     * Refresh all topics contents
     * starting from get topics for all categories
     */
    @GetMapping(path = "/admin/refresh")
    @ResponseStatus(HttpStatus.OK)
    void refreshTopics() {
        topicMessager.getTopicCategories();
    }
}
