package com.nobodyhub.transcendence.hub.topic.service.sub;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuColumn;

import java.util.Optional;

public interface ZhihuColumnService {
    /**
     * Find existing topic related with given Zhihu column
     *
     * @param zhihuColumn
     * @return if found, return directly without merging.
     * if not found, will return newly created one.
     */
    Topic find(ZhihuColumn zhihuColumn);

    /**
     * Save zhihu topic to corresponding topic
     *
     * @param zhihuColumn
     * @return if found, merge with exsiting one. if not found, create new one
     */
    Topic save(ZhihuColumn zhihuColumn);

    /**
     * Find topic for given Zhihu column id
     *
     * @param columnId
     * @return
     */
    Optional<Topic> findByZhihuColumnId(String columnId);
}
