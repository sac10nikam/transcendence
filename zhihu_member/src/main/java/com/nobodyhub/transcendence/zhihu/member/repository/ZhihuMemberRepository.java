package com.nobodyhub.transcendence.zhihu.member.repository;

import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuAuthor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ZhihuMemberRepository extends CrudRepository<ZhihuAuthor, String> {
    Optional<ZhihuAuthor> findByUrlToken(String urlToken);
}
