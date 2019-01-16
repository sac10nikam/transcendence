package com.nobodyhub.transcendence.hub.tag.repository;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.domain.excerpt.TopicDataExcerpt;
import com.nobodyhub.transcendence.hub.repository.HubRepository;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Topic, String>, HubRepository<Topic, TopicDataExcerpt> {

}
