package com.nobodyhub.transcendence.hub.topic.service;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;

import java.util.Set;

public interface TopicService {
    /**
     * Save zhihu topic to corresponding topic
     *
     * @param topic zhihu topic to be saved
     */
    void saveZhihuTopic(ZhihuTopic topic);

    /**
     * Save parents of zhihu topic
     *
     * @param topicId topic whose parent to be updated
     * @param parents parent topics
     */
    void saveZhihuTopicParent(String topicId, Set<ZhihuTopic> parents);

    /**
     * Save childrent of zhihu topic
     *
     * @param topicId  topic whose parent to be updated
     * @param children child topics
     */
    void saveZhihuTopicChild(String topicId, Set<ZhihuTopic> children);
}
