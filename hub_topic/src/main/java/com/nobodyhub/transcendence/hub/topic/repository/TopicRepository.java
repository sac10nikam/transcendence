package com.nobodyhub.transcendence.hub.topic.repository;

import com.nobodyhub.transcendence.hub.domain.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic, String> {
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
    Optional<Topic> findFirstByDataIdAndType(String dataId, Topic.TopicType type);
}
