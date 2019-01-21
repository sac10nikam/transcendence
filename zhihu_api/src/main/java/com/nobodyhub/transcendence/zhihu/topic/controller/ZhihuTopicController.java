package com.nobodyhub.transcendence.zhihu.topic.controller;

import com.nobodyhub.transcendence.zhihu.topic.service.ZhihuTopicApiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zhihu/topics")
public class ZhihuTopicController {
    private final ZhihuTopicApiService topicApiService;

    public ZhihuTopicController(ZhihuTopicApiService topicApiService) {
        this.topicApiService = topicApiService;
    }

    /**
     * Get topic of given id
     *
     * @param topicId
     */
    @GetMapping(path = "/topicId/{topicId}")
    @ResponseStatus(HttpStatus.OK)
    void getTopicById(@PathVariable("topicId") String topicId) {
        topicApiService.getTopicById(topicId);
    }

    /**
     * Get parents topic of topic with given id
     *
     * @param topicId
     */
    @GetMapping(path = "/topicId/{topicId}/parents")
    @ResponseStatus(HttpStatus.OK)
    void getTopicParents(@PathVariable("topicId") String topicId) {
        topicApiService.getTopicParents(topicId);
    }

    /**
     * Get children topic of topic with given id
     *
     * @param topicId
     */
    @GetMapping(path = "/topicId/{topicId}/children")
    @ResponseStatus(HttpStatus.OK)
    void getTopicChildren(@PathVariable("topicId") String topicId) {
        topicApiService.getTopicChildrent(topicId);
    }

    /**
     * Refresh all topics contents
     * starting from get topics for all categories
     */
    @GetMapping(path = "/admin/refresh")
    @ResponseStatus(HttpStatus.OK)
    void refreshTopics(@RequestParam(value = "fetchTopics", defaultValue = "false") boolean fetchTopics) {
        topicApiService.getTopicCategories(fetchTopics);
    }
}
