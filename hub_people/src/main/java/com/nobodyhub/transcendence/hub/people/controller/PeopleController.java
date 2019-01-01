package com.nobodyhub.transcendence.hub.people.controller;

import com.nobodyhub.transcendence.hub.people.service.PeopleService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    /**
     * Save zhihu member
     *
     * @param zhihuMember
     */
    @PostMapping(path = "/zhihu")
    @ResponseStatus(HttpStatus.OK)
    void saveZhihuMember(@RequestBody ZhihuMember zhihuMember) {
        peopleService.saveZhihuMenber(zhihuMember);
    }
}
