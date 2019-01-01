package com.nobodyhub.transcendence.hub.people.repository;

import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.hub.repository.HubRepository;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PeopleRepository extends CrudRepository<People, String>, HubRepository<People, People.PeopleType> {
    /**
     * find people by name
     *
     * @param name
     * @return
     */
    Optional<People> findFirstByName(String name);

    /**
     * find people by {@link ZhihuMember#getUrlToken()}
     *
     * @param urlToken
     * @return
     */
    Optional<People> findFirstByZhihuMember_UrlToken(String urlToken);
}
