package com.nobodyhub.transcendence.zhihu.topic.service;

import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopic;

import java.util.List;
import java.util.Optional;

public interface ZhihuTopicService {
    /**
     * Get topic for specific id
     *
     * @param id
     * @return
     */
    Optional<ZhihuTopic> getById(String id);

    /**
     * Get a list of topics whose name contains given name
     *
     * @param name
     * @return
     */
    List<ZhihuTopic> getByName(String name);

    /**
     * Persist the given topic
     *
     * @param topic topic to be saved, can not be null, {@link ZhihuTopic#getId()} can not be null
     * @return
     */
    ZhihuTopic save(ZhihuTopic topic);
}
