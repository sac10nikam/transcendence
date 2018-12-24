package com.nobodyhub.transcendence.zhihu.member.service.impl;

import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiMember;
import com.nobodyhub.transcendence.zhihu.member.domain.ZhihuMember;
import com.nobodyhub.transcendence.zhihu.member.repository.ZhihuMemberRepository;
import com.nobodyhub.transcendence.zhihu.member.service.ZhihuMemberService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
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
        return repository.findFirstByUrlTokenOrderByUpdatedAtDesc(urlToken);
    }

    @Override
    public Optional<ZhihuMember> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public ZhihuMember save(ZhihuApiMember member) {
        Assert.notNull(member, "Author to be saved can not be null!");
        Assert.notNull(member.getId(), "Author need to have a non-null id field!");
        Optional<ZhihuMember> existed = repository.findFirstByDateIdOrderByUpdatedAtDesc(member.getId());
        if (existed.isPresent()) {
            ZhihuMember existedMember = existed.get();
            // get the merged object first
            ZhihuApiMember result = MergeUtils.merge(member, existedMember.getData());
            if (result != null && result.equals(existedMember.getData())) {
                // if after merge, the object is the same
                // update the MongoObject#updatedAt of existing object and save
                existedMember.setUpdatedAt(DateTime.now());
                return repository.save(existedMember);
            } else {
                // if after merge, the object changed
                // create new object
                ZhihuMember newMember = new ZhihuMember();
                newMember.setUpdatedAt(DateTime.now());
                newMember.setData(result);
                return repository.save(newMember);
            }
        } else {
            // no existed object, save directly
            ZhihuMember newMember = new ZhihuMember();
            newMember.setUpdatedAt(DateTime.now());
            newMember.setData(member);
            return repository.save(newMember);
        }
    }
}
