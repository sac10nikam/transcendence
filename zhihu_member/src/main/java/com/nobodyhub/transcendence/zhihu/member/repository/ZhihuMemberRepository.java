package com.nobodyhub.transcendence.zhihu.member.repository;

import com.nobodyhub.transcendence.zhihu.api.domain.ZhihuApiMember;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ZhihuMemberRepository extends CrudRepository<ZhihuApiMember, String> {
    Optional<ZhihuApiMember> findByUrlToken(String urlToken);
}
