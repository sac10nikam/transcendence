package com.nobodyhub.transcendence.zhihu.member.repository;

import com.nobodyhub.transcendence.zhihu.member.domain.ZhihuMember;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ZhihuMemberRepository extends CrudRepository<ZhihuMember, String> {
    /**
     * Get the latest records for given url token
     *
     * @param urlToken
     * @return
     */
    Optional<ZhihuMember> findFirstByUrlTokenOrderByUpdatedAtDesc(String urlToken);

    /**
     * Get the lastest records for the given data id
     *
     * @param dataId
     * @return
     */
    Optional<ZhihuMember> findFirstByDateIdOrderByUpdatedAtDesc(String dataId);
}
