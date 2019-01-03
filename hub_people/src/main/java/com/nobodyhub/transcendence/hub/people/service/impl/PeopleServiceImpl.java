package com.nobodyhub.transcendence.hub.people.service.impl;

import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.hub.people.client.ZhihuMemberApiClient;
import com.nobodyhub.transcendence.hub.people.repository.PeopleRepository;
import com.nobodyhub.transcendence.hub.people.service.PeopleService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nobodyhub.transcendence.hub.domain.People.PeopleType.ZHIHU_MEMBER;

@Slf4j
@Service
public class PeopleServiceImpl implements PeopleService {
    private final PeopleRepository peopleRepository;

    private final ZhihuMemberApiClient zhihuMemberApiClient;

    public PeopleServiceImpl(PeopleRepository peopleRepository,
                             ZhihuMemberApiClient zhihuMemberApiClient) {
        this.peopleRepository = peopleRepository;
        this.zhihuMemberApiClient = zhihuMemberApiClient;
    }

    @Override
    public People save(ZhihuMember zhihuMember) {
        Optional<People> people = peopleRepository.findFirstByDataIdAndType(zhihuMember.getUrlToken(), ZHIHU_MEMBER);
        if (people.isPresent()) {
            People existPeople = people.get();
            ZhihuMember exist = existPeople.getZhihuMember();
            existPeople.setZhihuMember(MergeUtils.merge(zhihuMember, exist));
            return peopleRepository.save(existPeople);
        }
        return createFromZhihuMember(zhihuMember);
    }


    @Override
    public People find(ZhihuMember zhihuMember) {
        Optional<People> people = peopleRepository.findFirstByDataIdAndType(zhihuMember.getUrlToken(), ZHIHU_MEMBER);
        return people.orElseGet(() -> createFromZhihuMember(zhihuMember));
    }

    @Override
    public Optional<People> findByZhihuMemberUrlToken(String urlToken) {
        Optional<People> people = peopleRepository.findFirstByDataIdAndType(urlToken, ZHIHU_MEMBER);
        if (!people.isPresent()) {
            zhihuMemberApiClient.getByMemberUrlToken(urlToken);
        }
        return people;
    }

    private People createFromZhihuMember(ZhihuMember zhihuMember) {
        People people = new People();
        people.setDataId(zhihuMember.getUrlToken());
        people.setName(zhihuMember.getName());
        people.setType(ZHIHU_MEMBER);
        people.setZhihuMember(zhihuMember);
        return peopleRepository.save(people);
    }

    @Override
    public List<People> findByPeopleName(String name) {
        return peopleRepository.findByName(name);
    }
}
