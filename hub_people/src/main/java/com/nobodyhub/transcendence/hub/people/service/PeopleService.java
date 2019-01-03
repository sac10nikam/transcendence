package com.nobodyhub.transcendence.hub.people.service;

import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.hub.people.service.sub.ZhihuMemberService;

import java.util.List;

public interface PeopleService extends ZhihuMemberService {
    /**
     * Find a list of people with given name
     *
     * @param name
     * @return
     */
    List<People> findByPeopleName(String name);
}
