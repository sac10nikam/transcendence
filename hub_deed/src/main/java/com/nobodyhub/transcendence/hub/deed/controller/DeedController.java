package com.nobodyhub.transcendence.hub.deed.controller;

import com.nobodyhub.transcendence.hub.deed.service.DeedService;
import com.nobodyhub.transcendence.hub.domain.Deed;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deeds")
public class DeedController {
    private final DeedService deedService;

    public DeedController(DeedService deedService) {
        this.deedService = deedService;
    }

    @PostMapping("/zhihu-answer/save")
    Deed saveZhihuAnswer(ZhihuAnswer zhihuAnswer) {
        return this.deedService.save(zhihuAnswer);
    }

    @PostMapping("/zhihu-answer/save/no-return")
    @ResponseStatus(HttpStatus.OK)
    void saveZhihuAnswerNoReturn(ZhihuAnswer zhihuAnswer) {
        this.deedService.save(zhihuAnswer);
    }

    @PostMapping("/zhihu-answer/get")
    Deed getByZhihuAnswer(ZhihuAnswer zhihuAnswer) {
        return this.deedService.find(zhihuAnswer);
    }

    @PostMapping("/zhihu-comment/save")
    Deed saveZhihuComment(ZhihuComment zhihuComment) {
        return this.deedService.save(zhihuComment);
    }

    @PostMapping("/zhihu-comment/save/no-return")
    @ResponseStatus(HttpStatus.OK)
    void saveZhihuCommentNoReturn(ZhihuComment zhihuComment) {
        this.deedService.save(zhihuComment);
    }

    @PostMapping("/zhihu-comment/get")
    Deed getByZhihuComment(ZhihuComment zhihuComment) {
        return this.deedService.find(zhihuComment);
    }
}
