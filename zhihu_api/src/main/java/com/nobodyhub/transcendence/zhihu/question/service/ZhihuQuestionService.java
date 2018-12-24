package com.nobodyhub.transcendence.zhihu.question.service;

import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiQuestion;

import java.util.Optional;

public interface ZhihuQuestionService {
    /**
     * Find the Zhihu Question by given question id
     *
     * @param id
     * @return
     */
    Optional<ZhihuApiQuestion> findById(String id);

    /**
     * Save, or merge if exist, the given question
     *
     * @param question
     * @return the saved/merged question
     */
    ZhihuApiQuestion save(ZhihuApiQuestion question);
}
