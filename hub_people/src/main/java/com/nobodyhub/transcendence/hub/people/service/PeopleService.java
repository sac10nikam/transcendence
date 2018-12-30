package com.nobodyhub.transcendence.hub.people.service;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;

public interface PeopleService {
    /**
     * Save zhihu member to corresponding individual
     *
     * @param member zhihu member information to be saved
     */
    void saveZhihuMenber(ZhihuMember member);
}
