package com.nobodyhub.transcendence.hub.topic.service.sub;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;

import java.util.Optional;
import java.util.Set;

public interface ZhihuTopicService {
    /**
     * Find existing topic related with given Zhihu Topic
     *
     * @param zhihuTopic
     * @return if found, return directly without merging.
     * if not found, will return newly created one.
     */
    Topic find(ZhihuTopic zhihuTopic);

    /**
     * Save zhihu topic to corresponding topic
     *
     * @param zhihuTopic zhihu topic to be saved
     * @return if found, merge with exsiting one. if not found, create new one
     */
    Topic save(ZhihuTopic zhihuTopic);

    /**
     * Save parents of zhihu topic
     *
     * @param topicId topic whose parent to be updated
     * @param parents parent topics
     */
    void saveZhihuTopicParents(String topicId, Set<ZhihuTopic> parents);

    /**
     * Save children of zhihu topic
     *
     * @param topicId  topic whose parent to be updated
     * @param children child topics
     */
    void saveZhihuTopicChildren(String topicId, Set<ZhihuTopic> children);

    /**
     * Find topic for given Zhihu topic id
     *
     * @param topicId
     * @return
     */
    Optional<Topic> findByZhihuTopicId(String topicId);
}
