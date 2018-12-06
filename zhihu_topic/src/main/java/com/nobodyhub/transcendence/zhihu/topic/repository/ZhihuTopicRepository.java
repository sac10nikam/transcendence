package com.nobodyhub.transcendence.zhihu.topic.repository;

import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopic;
import org.springframework.data.repository.CrudRepository;

public interface ZhihuTopicRepository extends CrudRepository<ZhihuTopic, String> {
}
