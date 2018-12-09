package com.nobodyhub.transcendence.zhihu.topic.service;

import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopicCategory;

import java.util.List;

/**
 * Parse pages related with Zhihu Topics
 */
public interface ZhihuTopicPageParseService {
    /**
     * Get all topic categories from https://www.zhihu.com/topics
     *
     * @return
     */
    List<ZhihuTopicCategory> getAllCategories();

}
