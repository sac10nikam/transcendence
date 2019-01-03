package com.nobodyhub.transcendence.hub.people.controller;

import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.hub.people.service.PeopleService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    @PostMapping(path = "/zhihu-member/save")
    @ResponseStatus(HttpStatus.OK)
    People saveZhihuMember(@RequestBody ZhihuMember zhihuMember) {
        return peopleService.save(zhihuMember);
    }

    /**
     * Same as {@link #saveZhihuMember(ZhihuMember)} with return no value
     * used by Api update which does not care about the return value
     *
     * @param zhihuMember
     */
    @PostMapping(path = "/zhihu-member/save/no-return")
    @ResponseStatus(HttpStatus.OK)
    void saveZhihuMemberNoReturn(@RequestBody ZhihuMember zhihuMember) {
        peopleService.save(zhihuMember);
    }

    /**
     * Save zhihu member
     *
     * @param zhihuMember
     */
    @PostMapping(path = "/zhihu-member/get")
    @ResponseStatus(HttpStatus.OK)
    People getByZhihuMember(@RequestBody ZhihuMember zhihuMember) {
        return peopleService.find(zhihuMember);
    }

    @GetMapping(path = "/zhihu-member/get/{urlToken}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<People> getByZhihuMemberToken(@PathVariable String urlToken) {
        Optional<People> people = peopleService.findByZhihuMemberUrlToken(urlToken);
        return people.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
