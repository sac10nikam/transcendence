package com.nobodyhub.transcendence.hub.topic.service;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;

import java.util.Set;

public interface TopicService {
    /**
     * Find existing topic related with given Zhihu Topic
     *
     * @param zhihuTopic
     * @return if found, return directly without merging.
     * if not found, will return newly created one.
     */
    Topic find(ZhihuTopic zhihuTopic);

    /**
     * Find existing topic related with given Zhihu question
     *
     * @param zhihuQuestion
     * @return if found, return directly without merging.
     * if not found, will return newly created one.
     */
    Topic find(ZhihuQuestion zhihuQuestion);

    /**
     * Save zhihu topic to corresponding topic
     *
     * @param zhihuTopic zhihu topic to be saved
     * @return if found, merge with exsiting one. if not found, create new one
     */
    Topic save(ZhihuTopic zhihuTopic);

    /**
     * Save zhihu topic to corresponding topic
     *
     * @param zhihuQuestion zhihu topic to be saved
     * @return if found, merge with exsiting one. if not found, create new one
     */
    Topic save(ZhihuQuestion zhihuQuestion);

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


}
