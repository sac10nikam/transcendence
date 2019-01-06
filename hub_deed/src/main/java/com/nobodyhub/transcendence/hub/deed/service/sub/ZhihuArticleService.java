package com.nobodyhub.transcendence.hub.deed.service.sub;

import com.nobodyhub.transcendence.hub.domain.Deed;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuArticle;

import java.util.Optional;

public interface ZhihuArticleService {
    /**
     * Save Zhihu Article
     *
     * @param article
     */
    Deed save(ZhihuArticle article);

    /**
     * Find by Zhihu Article
     *
     * @param article
     */
    Deed find(ZhihuArticle article);

    /**
     * Find by Zhihu answer id, {@link ZhihuArticle#getId()}
     *
     * @param articleId
     * @return
     */
    Optional<Deed> findByZhihuArticleId(String articleId);
}
