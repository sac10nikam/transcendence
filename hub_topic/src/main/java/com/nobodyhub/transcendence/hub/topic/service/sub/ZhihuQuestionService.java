package com.nobodyhub.transcendence.hub.topic.service.sub;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;

import java.util.Optional;

public interface ZhihuQuestionService {
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
     * @param zhihuQuestion zhihu topic to be saved
     * @return if found, merge with exsiting one. if not found, create new one
     */
    Topic save(ZhihuQuestion zhihuQuestion);

    /**
     * Find topic for given Zhihu question id
     *
     * @param questionId
     * @return
     */
    Optional<Topic> findByZhihuQuestionId(String questionId);
}
