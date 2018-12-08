package com.nobodyhub.transcendence.zhihu.member.service.impl;

import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuMember;
import com.nobodyhub.transcendence.zhihu.domain.merge.MergeUtils;
import com.nobodyhub.transcendence.zhihu.member.repository.ZhihuMemberRepository;
import com.nobodyhub.transcendence.zhihu.member.service.ZhihuMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Slf4j
@Service
public class ZhihuMemberServiceImpl implements ZhihuMemberService {
    private final ZhihuMemberRepository repository;

    public ZhihuMemberServiceImpl(ZhihuMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ZhihuMember> findByUrlToken(String urlToken) {
        return repository.findByUrlToken(urlToken);
    }

    @Override
    public Optional<ZhihuMember> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public ZhihuMember save(ZhihuMember author) {
        Assert.notNull(author, "Author to be saved can not be null!");
        Assert.notNull(author.getId(), "Author need to have a non-null id field!");
        Optional<ZhihuMember> existed = repository.findById(author.getId());
        if (existed.isPresent()) {
            //merge the old one and new one
            return MergeUtils.merge(author, existed.get());
        } else {
            // no existed object, save directly
            return repository.save(author);
        }
    }
}
