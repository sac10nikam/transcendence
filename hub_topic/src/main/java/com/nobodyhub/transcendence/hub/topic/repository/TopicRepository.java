package com.nobodyhub.transcendence.hub.topic.repository;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.domain.Topic.TopicType;
import com.nobodyhub.transcendence.hub.repository.HubRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic, String>, HubRepository<Topic, TopicType> {
    /**
     * find topic by name
     *
     * @param name
     * @return
     */
    Optional<Topic> findFirstByName(String name);

    /**
     * find topic by {@link Topic#getDataId()}
     *
     * @param dataId
     * @return
     */
    Optional<Topic> findFirstByDataIdAndType(String dataId, TopicType type);
}
