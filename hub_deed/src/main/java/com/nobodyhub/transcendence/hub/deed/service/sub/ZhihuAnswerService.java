package com.nobodyhub.transcendence.hub.deed.service.sub;

import com.nobodyhub.transcendence.hub.domain.Deed;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;

import java.util.Optional;

public interface ZhihuAnswerService {
    /**
     * Save Zhihu Answer
     *
     * @param answer
     */
    Deed save(ZhihuAnswer answer);

    /**
     * Find by Zhihu Answer
     *
     * @param answer
     */
    Deed find(ZhihuAnswer answer);

    /**
     * Find by Zhihu answer id, {@link ZhihuAnswer#getId()}
     *
     * @param answerId
     * @return
     */
    Optional<Deed> findByZhihuAnswerId(String answerId);
}
