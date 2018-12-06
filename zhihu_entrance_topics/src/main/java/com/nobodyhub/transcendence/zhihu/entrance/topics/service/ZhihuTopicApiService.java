package com.nobodyhub.transcendence.zhihu.entrance.topics.service;

import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopicCategory;

import java.util.List;
import java.util.Optional;

public interface ZhihuTopicApiService {

    /**
     * Get Topic by id
     *
     * @param topicId
     * @return
     */
    Optional<ZhihuTopic> getTopic(String topicId);

    /**
     * Get topic ids of given category
     *
     * @param category
     * @return
     */
    List<ZhihuTopic> getTopicsByCategory(ZhihuTopicCategory category);
}
