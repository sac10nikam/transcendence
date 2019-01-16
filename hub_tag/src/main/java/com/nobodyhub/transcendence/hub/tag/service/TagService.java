package com.nobodyhub.transcendence.hub.tag.service;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.tag.service.sub.ZhihuTopicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TagService extends ZhihuTopicService {
    /**
     * Find Topic by given name
     *
     * @param name
     * @return
     */
    Page<Topic> findByName(String name, Pageable pageable);
}
