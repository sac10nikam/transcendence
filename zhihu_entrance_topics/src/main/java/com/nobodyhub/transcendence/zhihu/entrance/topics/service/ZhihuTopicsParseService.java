package com.nobodyhub.transcendence.zhihu.entrance.topics.service;

import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopicCategory;

import java.util.List;

/**
 * Parse the topics from the topics page: https://www.zhihu.com/topics
 */
public interface ZhihuTopicsParseService {
    /**
     * Get all topic categories
     *
     * @return
     */
    List<ZhihuTopicCategory> getAllCategories();

    /**
     * Get all topics of given category
     */
    List<ZhihuTopic> getAllTopicsOfCategory(ZhihuTopicCategory category);

}
