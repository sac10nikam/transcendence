package com.nobodyhub.transcendence.zhihu.member.controller;

import com.google.common.collect.Sets;
import com.nobodyhub.transcendence.common.util.FieldUtils;
import com.nobodyhub.transcendence.zhihu.api.domain.ZhihuMember;
import com.nobodyhub.transcendence.zhihu.member.service.ZhihuMemberService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/members")
public class ZhihuMemberController {

    private final ZhihuMemberService memberService;

    public ZhihuMemberController(ZhihuMemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/url_token/{urlToken}")
    ZhihuMember getByUrlToken(@PathVariable("urlToken") String urlToken,
                              @RequestParam(value = "includes", required = false, defaultValue = "") String includes) {
        Optional<ZhihuMember> author = memberService.findByUrlToken(urlToken);
        return author.map(a -> FieldUtils.filter(author.get(), splitByComma(includes)))
            .orElseGet(ZhihuMember::new);
    }

    @GetMapping("/id/{id}")
    ZhihuMember getById(@PathVariable("id") String urlToken,
                        @RequestParam(value = "includes", required = false, defaultValue = "") String includes) {
        Optional<ZhihuMember> author = memberService.findById(urlToken);
        return author.map(a -> FieldUtils.filter(author.get(), splitByComma(includes)))
            .orElseGet(ZhihuMember::new);
    }

    @PostMapping
    ZhihuMember save(@RequestBody ZhihuMember member) {
        return this.memberService.save(member);
    }

    /**
     * Split the contents of whole into a Set
     *
     * @param whole
     * @return
     */
    private Set<String> splitByComma(String whole) {
        return Sets.newHashSet(whole.split(","));
    }

}
