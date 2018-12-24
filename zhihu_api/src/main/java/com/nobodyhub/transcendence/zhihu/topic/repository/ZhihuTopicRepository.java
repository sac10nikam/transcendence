package com.nobodyhub.transcendence.zhihu.topic.repository;

import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZhihuTopicRepository extends CrudRepository<ZhihuTopic, String> {
    /**
     * Find all Topics whose name contains given literals
     *
     * @param name
     * @return
     */
    List<ZhihuTopic> findAllByName(String name);
}
