package com.nobodyhub.transcendence.zhihu.member.service;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;

import java.util.Optional;

/**
 * Service to manage the Zhihu Member
 */
public interface ZhihuMemberService {
    /**
     * Find the Zhihu member by the url toker
     *
     * @param urlToken
     * @return
     */
    Optional<com.nobodyhub.transcendence.zhihu.member.domain.ZhihuMember> findByUrlToken(String urlToken);

    /**
     * Find the Zhihu member by the id
     *
     * @param id
     * @return
     */
    Optional<com.nobodyhub.transcendence.zhihu.member.domain.ZhihuMember> findById(String id);

    /**
     * save the given Zhihu author
     *
     * @param author
     * @return
     */
    com.nobodyhub.transcendence.zhihu.member.domain.ZhihuMember save(ZhihuMember author);
}
