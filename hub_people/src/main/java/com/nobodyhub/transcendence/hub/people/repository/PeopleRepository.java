package com.nobodyhub.transcendence.hub.people.repository;

import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.hub.domain.excerpt.PeopleDataExcerpt;
import com.nobodyhub.transcendence.hub.repository.HubRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PeopleRepository extends CrudRepository<People, String>, HubRepository<People, PeopleDataExcerpt> {
    /**
     * find people by name
     *
     * @param name
     * @return
     */
    List<People> findByName(String name);
}
