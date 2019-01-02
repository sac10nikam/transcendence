package com.nobodyhub.transcendence.zhihu.question.controller;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import com.nobodyhub.transcendence.zhihu.question.service.ZhihuQuestionApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class ZhihuQuestionController {
    private final ZhihuQuestionApiService questionApiService;

    public ZhihuQuestionController(ZhihuQuestionApiService questionApiService) {
        this.questionApiService = questionApiService;
    }

    /**
     * Get question by id, {@link ZhihuQuestion#getId()}
     *
     * @param id
     */
    @GetMapping("/id/{id}")
    void getQuestionById(@PathVariable("id") String id) {
        questionApiService.getQuestion(id);
    }
}
