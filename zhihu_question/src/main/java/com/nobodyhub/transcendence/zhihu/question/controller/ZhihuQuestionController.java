package com.nobodyhub.transcendence.zhihu.question.controller;

import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuQuestion;
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
    ZhihuQuestion findById(@PathVariable("id") String id) {
        return zhihuQuestionService.findById(id).orElse(new ZhihuQuestion());
    }

    @PostMapping
    ZhihuQuestion save(@RequestBody ZhihuQuestion question) {
        return zhihuQuestionService.save(question);
    }
}
