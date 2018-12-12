package com.nobodyhub.transcendence.zhihu.question.controller;

import com.nobodyhub.transcendence.zhihu.api.domain.ZhihuApiQuestion;
import com.nobodyhub.transcendence.zhihu.question.service.ZhihuQuestionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class ZhihuQuestionController {
    private final ZhihuQuestionService zhihuQuestionService;

    public ZhihuQuestionController(ZhihuQuestionService zhihuQuestionService) {
        this.zhihuQuestionService = zhihuQuestionService;
    }

    @GetMapping("/id/{id}")
    ZhihuApiQuestion findById(@PathVariable("id") String id) {
        return zhihuQuestionService.findById(id).orElse(new ZhihuApiQuestion());
    }

    @PostMapping
    ZhihuApiQuestion save(@RequestBody ZhihuApiQuestion question) {
        return zhihuQuestionService.save(question);
    }
}
