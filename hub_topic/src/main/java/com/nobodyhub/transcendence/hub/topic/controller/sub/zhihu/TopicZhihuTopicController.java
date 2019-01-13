package com.nobodyhub.transcendence.hub.topic.controller.sub.zhihu;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controller for Zhihu Topic
 */
@RestController
@RequestMapping("/topics/zhihu/topic")
public class TopicZhihuTopicController {
    private final TopicService topicService;

    public TopicZhihuTopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Get by zhihu topic
     *
     * @param zhihuTopic
     */
    @PostMapping(path = "/get")
    Topic getByZhihuTopic(@RequestBody ZhihuTopic zhihuTopic) {
        return topicService.find(zhihuTopic);
    }

    /**
     * Save zhihu topic
     *
     * @param zhihuTopic
     */
    @PostMapping(path = "/save")
    Topic saveZhihuTopic(@RequestBody ZhihuTopic zhihuTopic) {
        return topicService.save(zhihuTopic);
    }

    /**
     * Save zhihu topic without returning the saved topic
     *
     * @param zhihuTopic
     */
    @PostMapping(path = "/topic/save/no-return")
    @ResponseStatus(HttpStatus.OK)
    void saveZhihuTopicNoReturn(@RequestBody ZhihuTopic zhihuTopic) {
        topicService.save(zhihuTopic);
    }

    /**
     * Save zhihu parent topics
     *
     * @param topicId
     * @param parents
     */
    @PostMapping(path = "/parent/{topicId}")
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
    @PostMapping(path = "/children/{topicId}")
    @ResponseStatus(HttpStatus.OK)
    void saveZhihuChildrenTopic(@PathVariable("topicId") String topicId,
                                @RequestBody Set<ZhihuTopic> children) {
        topicService.saveZhihuTopicChildren(topicId, children);
    }
}
