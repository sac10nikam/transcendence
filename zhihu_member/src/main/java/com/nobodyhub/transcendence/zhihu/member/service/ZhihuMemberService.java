package com.nobodyhub.transcendence.zhihu.member.service;

import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuMember;

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
    Optional<ZhihuMember> findByUrlToken(String urlToken);

    /**
     * Find the Zhihu member by the id
     *
     * @param id
     * @return
     */
    Optional<ZhihuMember> findById(String id);

    /**
     * save the given Zhihu author
     *
     * @param author
     * @return
     */
    ZhihuMember save(ZhihuMember author);
}
