package com.nobodyhub.transcendence.hub.topic.repository;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.domain.Topic.TopicType;
import com.nobodyhub.transcendence.hub.domain.abstr.TopicDataExcerpt;
import com.nobodyhub.transcendence.hub.repository.HubRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic, String>, HubRepository<Topic, TopicType> {
    /**
     * find topic by Topic#getDataId()
     *
     * @param dataId
     * @return
     */
    @Deprecated
    Optional<Topic> findFirstByDataIdAndType(String dataId, TopicType type);

    /**
     * Find Topic by excerpt information
     *
     * @param excerpt
     * @return
     */
    Optional<Topic> findFirstByExcerptsContains(TopicDataExcerpt excerpt);
}
