package com.nobodyhub.transcendence.zhihu.topic.service;

import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiAnswer;
import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopicCategory;

import java.util.List;
import java.util.Optional;

public interface ZhihuTopicApiService {

    /**
     * Get Topic by id, include children/parent
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

    /**
     * Get the answers of given Topic
     *
     * @param topicId id of the topic
     * @return
     */
    List<ZhihuApiAnswer> getAnswerByTopic(String topicId);
}
