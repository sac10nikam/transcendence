package com.nobodyhub.transcendence.hub.people.service.sub;

import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;

import java.util.Optional;

public interface ZhihuMemberService {
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

    /**
     * Find People by Zhihu member urlToken{@link ZhihuMember#getUrlToken()}
     *
     * @param urlToken
     * @return
     */
    Optional<People> findByZhihuMemberUrlToken(String urlToken);
}
