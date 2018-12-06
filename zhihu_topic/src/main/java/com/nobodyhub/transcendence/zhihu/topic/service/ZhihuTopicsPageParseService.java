package com.nobodyhub.transcendence.zhihu.topic.service;

import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopicCategory;

import java.util.List;

/**
 * Parse the topics from the topics page: https://www.zhihu.com/topics
 */
public interface ZhihuTopicsPageParseService {
    /**
     * Get all topic categories
     *
     * @return
     */
    List<ZhihuTopicCategory> getAllCategories();
}
