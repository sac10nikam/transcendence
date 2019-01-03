package com.nobodyhub.transcendence.hub.topic.service;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.service.sub.ZhihuQuestionService;
import com.nobodyhub.transcendence.hub.topic.service.sub.ZhihuTopicService;

import java.util.List;

public interface TopicService extends ZhihuTopicService, ZhihuQuestionService {
    /**
     * Find Topic by given name
     *
     * @param name
     * @return
     */
    List<Topic> findByName(String name);
}
