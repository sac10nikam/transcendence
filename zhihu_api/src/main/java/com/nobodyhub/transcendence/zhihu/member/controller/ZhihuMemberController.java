package com.nobodyhub.transcendence.zhihu.member.controller;

import com.google.common.collect.Sets;
import com.nobodyhub.transcendence.common.util.FieldUtils;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiMember;
import com.nobodyhub.transcendence.zhihu.member.domain.ZhihuMember;
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
    ZhihuApiMember getByUrlToken(@PathVariable("urlToken") String urlToken,
                                 @RequestParam(value = "includes", required = false, defaultValue = "") String includes) {
        Optional<ZhihuMember> author = memberService.findByUrlToken(urlToken);
        return author.map(a -> FieldUtils.filter(author.get().getData(), splitByComma(includes)))
            .orElseGet(ZhihuApiMember::new);
    }

    @GetMapping("/id/{id}")
    ZhihuApiMember getById(@PathVariable("id") String urlToken,
                           @RequestParam(value = "includes", required = false, defaultValue = "") String includes) {
        Optional<ZhihuMember> author = memberService.findById(urlToken);
        return author.map(a -> FieldUtils.filter(author.get().getData(), splitByComma(includes)))
            .orElseGet(ZhihuApiMember::new);
    }

    @PostMapping
    ZhihuApiMember save(@RequestBody ZhihuApiMember member) {
        return this.memberService.save(member).getData();
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
