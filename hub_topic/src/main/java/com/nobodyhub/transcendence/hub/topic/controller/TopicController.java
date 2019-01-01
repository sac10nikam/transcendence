package com.nobodyhub.transcendence.hub.topic.controller;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Get by zhihu topic
     *
     * @param zhihuTopic
     */
    @PostMapping(path = "/zhihu-topic/get")
    Topic getByZhihuTopic(@RequestBody ZhihuTopic zhihuTopic) {
        return topicService.find(zhihuTopic);
    }

    /**
     * Save zhihu topic
     *
     * @param zhihuTopic
     */
    @PostMapping(path = "/zhihu-topic/save")
    Topic saveZhihuTopic(@RequestBody ZhihuTopic zhihuTopic) {
        return topicService.save(zhihuTopic);
    }

    /**
     * Save zhihu parent topics
     *
     * @param topicId
     * @param parents
     */
    @PostMapping(path = "/zhihu-topic/parent/{topicId}")
    @ResponseStatus(HttpStatus.OK)
    void saveZhihuParentsTopic(@PathVariable("topicId") String topicId,
                               @RequestBody Set<ZhihuTopic> parents) {
        topicService.saveZhihuTopicParents(topicId, parents);
    }

    /**
     * Save zhihu children topics
     *
     * @param topicId
     * @param children
     */
    @PostMapping(path = "/zhihu-topic/children/{topicId}")
    @ResponseStatus(HttpStatus.OK)
    void saveZhihuChildrenTopic(@PathVariable("topicId") String topicId,
                                @RequestBody Set<ZhihuTopic> children) {
        topicService.saveZhihuTopicChildren(topicId, children);
    }

    /**
     * Get by zhihu question
     *
     * @param zhihuQuestion
     */
    @PostMapping(path = "/zhihu-question/get")
    Topic getByZhihuQuestion(@RequestBody ZhihuQuestion zhihuQuestion) {
        return topicService.find(zhihuQuestion);
    }

    /**
     * Save zhihu question
     *
     * @param zhihuQuestion
     */
    @PostMapping(path = "/zhihu-topic/save")
    Topic saveZhihuTopic(@RequestBody ZhihuQuestion zhihuQuestion) {
        return topicService.save(zhihuQuestion);
    }
}
