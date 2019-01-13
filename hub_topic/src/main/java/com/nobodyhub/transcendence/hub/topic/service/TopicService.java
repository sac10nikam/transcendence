package com.nobodyhub.transcendence.hub.topic.service;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.service.sub.ZhihuColumnService;
import com.nobodyhub.transcendence.hub.topic.service.sub.ZhihuQuestionService;
import com.nobodyhub.transcendence.hub.topic.service.sub.ZhihuTopicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TopicService extends ZhihuTopicService, ZhihuQuestionService, ZhihuColumnService {
    /**
     * Find Topic by given name
     *
     * @param name
     * @return
     */
    Page<Topic> findByName(String name, Pageable pageable);
}
