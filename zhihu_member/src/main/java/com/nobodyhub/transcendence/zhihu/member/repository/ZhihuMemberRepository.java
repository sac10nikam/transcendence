package com.nobodyhub.transcendence.zhihu.member.repository;

import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuMember;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ZhihuMemberRepository extends CrudRepository<ZhihuMember, String> {
    Optional<ZhihuMember> findByUrlToken(String urlToken);
}
