package com.nobodyhub.transcendence.hub.deed.service;

import com.nobodyhub.transcendence.hub.deed.service.sub.ZhihuAnswerService;
import com.nobodyhub.transcendence.hub.deed.service.sub.ZhihuCommentService;
import com.nobodyhub.transcendence.hub.domain.Deed;
import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.hub.domain.Topic;

import java.awt.print.Pageable;
import java.util.List;

public interface DeedService extends ZhihuCommentService, ZhihuAnswerService {



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
    List<Deed> findAllChildren(String parentId, Pageable pageable);
}
