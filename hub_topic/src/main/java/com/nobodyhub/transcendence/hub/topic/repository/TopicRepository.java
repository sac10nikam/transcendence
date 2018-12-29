package com.nobodyhub.transcendence.hub.topic.repository;

import com.nobodyhub.transcendence.hub.topic.domain.Topic;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic, String> {
    /**
     * find topic by name
     *
     * @param name
     * @return
     */
    List<Topic> findByName(String name);

    /**
     * find topic by {@link ZhihuTopic#getId()}
     *
     * @param id
     * @return
     */
    List<Topic> findByZhihuTopic_Id(String id);
}
