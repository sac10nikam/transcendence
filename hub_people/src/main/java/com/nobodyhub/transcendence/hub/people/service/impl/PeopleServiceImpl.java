package com.nobodyhub.transcendence.hub.people.service.impl;

import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.hub.people.repository.PeopleRepository;
import com.nobodyhub.transcendence.hub.people.service.PeopleService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PeopleServiceImpl implements PeopleService {
    private final PeopleRepository peopleRepository;

    public PeopleServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public void saveZhihuMenber(ZhihuMember member) {
        Optional<People> people = peopleRepository.findFirstByZhihuMember_UrlToken(member.getUrlToken());
        if (people.isPresent()) {
            People existPeople = people.get();
            ZhihuMember exist = existPeople.getZhihuMember();
            if (exist != null) {
                existPeople.setZhihuMember(MergeUtils.merge(member, exist));
            } else {
                existPeople.setZhihuMember(member);
            }
            peopleRepository.save(existPeople);
        } else {
            People newPeople = new People();
            newPeople.setName(member.getName());
            newPeople.setZhihuMember(member);
            peopleRepository.save(newPeople);
        }
    }
}
