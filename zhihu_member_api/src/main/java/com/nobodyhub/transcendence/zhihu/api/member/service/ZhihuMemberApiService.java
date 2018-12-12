package com.nobodyhub.transcendence.zhihu.api.member.service;

import com.nobodyhub.transcendence.zhihu.api.domain.ZhihuApiMember;

import java.util.Optional;

/**
 * Service to handle the request using Zhihu member API
 */
public interface ZhihuMemberApiService {
    /**
     * Get member by the url token via
     * https://www.zhihu.com/api/v4/members/{urlToken}
     *
     * @param urlToken
     * @return Optional.empty() if encounter error
     */
    Optional<ZhihuApiMember> getByUrlToken(String urlToken);
}
