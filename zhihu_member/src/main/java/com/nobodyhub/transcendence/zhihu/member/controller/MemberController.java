package com.nobodyhub.transcendence.zhihu.member.controller;

import com.google.common.collect.Sets;
import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuAuthor;
import com.nobodyhub.transcendence.zhihu.domain.util.FieldUtils;
import com.nobodyhub.transcendence.zhihu.member.service.ZhihuMemberService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final ZhihuMemberService memberService;

    public MemberController(ZhihuMemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/url_token/{urlToken}")
    ZhihuAuthor getByUrlToken(@PathVariable("urlToken") String urlToken,
                              @RequestParam(value = "includes", required = false, defaultValue = "") String includes) {
        Optional<ZhihuAuthor> author = memberService.findByUrlToken(urlToken);
        return author.map(a -> FieldUtils.filter(author.get(), splitByComma(includes)))
            .orElseGet(ZhihuAuthor::new);
    }

    @GetMapping("/id/{id}")
    ZhihuAuthor getById(@PathVariable("id") String urlToken,
                        @RequestParam(value = "includes", required = false, defaultValue = "") String includes) {
        Optional<ZhihuAuthor> author = memberService.findById(urlToken);
        return author.map(a -> FieldUtils.filter(author.get(), splitByComma(includes)))
            .orElseGet(ZhihuAuthor::new);
    }

    @PostMapping
    ZhihuAuthor save(@RequestBody ZhihuAuthor author) {
        return this.memberService.save(author);
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
