package com.nobodyhub.transcendence.hub.deed.service;

import com.nobodyhub.transcendence.hub.domain.Deed;
import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;

import java.awt.print.Pageable;
import java.util.List;

public interface DeedService {
    /**
     * Saven Zhihu Answer
     *
     * @param answer
     */
    void save(ZhihuAnswer answer);

    /**
     * Save Zhihu comment
     *
     * @param comment
     */
    void save(ZhihuComment comment);

    /**
     * Find all deeds belong to {@link Topic#getName()}
     *
     * @param name
     * @return
     */
    List<Deed> findByTopicName(String name, Pageable pageable);

    /**
     * Find all deeds related to {@link People#getName()}
     *
     * @param name
     * @return
     */
    List<Deed> findByPeopleName(String name, Pageable pageable);

    /**
     * Find all deeds belonging to the parent with given id
     *
     * @param parentId
     * @param pageable
     * @return
     */
    List<Deed> findAllChilrent(String parentId, Pageable pageable);
}
