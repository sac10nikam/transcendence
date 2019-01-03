package com.nobodyhub.transcendence.hub.deed.service.sub;

import com.nobodyhub.transcendence.hub.domain.Deed;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;

import java.util.Optional;

public interface ZhihuCommentService {
    /**
     * Save Zhihu comment
     *
     * @param comment
     */
    Deed save(ZhihuComment comment);

    /**
     * Find by Zhihu comment
     *
     * @param comment
     */
    Deed find(ZhihuComment comment);

    /**
     * Find by Zhihu comment id, {@link ZhihuComment#getId()}
     *
     * @param commentId
     * @return
     */
    Optional<Deed> findByZhihuCommentId(String commentId);
}
