package com.nobodyhub.transcendence.zhihu.member.controller;

import com.nobodyhub.transcendence.zhihu.member.service.ZhihuMemberApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zhihu/members")
public class ZhihuMemberController {
    private final ZhihuMemberApiService memberApiService;

    public ZhihuMemberController(ZhihuMemberApiService memberApiService) {
        this.memberApiService = memberApiService;
    }

    @GetMapping(path = "/urlToken/{urlToken}")
    void getByMemberUrlToken(@PathVariable("urlToken") String urlToken) {
        memberApiService.getMember(urlToken);
    }
}
