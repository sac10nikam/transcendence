package com.nobodyhub.transcendence.zhihu.answer.controller;

import com.nobodyhub.transcendence.zhihu.answer.service.ZhihuAnswerApiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zhihu/answer")
public class ZhihuAnswerController {
    private final ZhihuAnswerApiService answerApiService;

    public ZhihuAnswerController(ZhihuAnswerApiService answerApiService) {
        this.answerApiService = answerApiService;
    }

    /**
     * Get answer of given id
     *
     * @param answerId
     */
    @GetMapping(path = "/answer/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    void getAnswerById(@PathVariable("answerId") String answerId) {
        answerApiService.getById(answerId);
    }

    /**
     * Get comments of answer with given id
     *
     * @param commentId
     */
    @GetMapping(path = "/comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    void getAnswerComments(@PathVariable("commentId") String commentId) {
        answerApiService.getCommentById(commentId);
    }
}
