package com.nobodyhub.transcendence.hub.people.service;

import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;

public interface PeopleService {
    /**
     * Save people with the contents of Zhihu member
     *
     * @param zhihuMember zhihu member to corresponding individual
     * @return
     */
    People save(ZhihuMember zhihuMember);

    /**
     * Find People by Zhihu member
     *
     * @param zhihuMember
     * @return
     */
    People find(ZhihuMember zhihuMember);
}
