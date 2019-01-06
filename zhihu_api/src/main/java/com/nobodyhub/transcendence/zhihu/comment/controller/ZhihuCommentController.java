package com.nobodyhub.transcendence.zhihu.comment.controller;

import com.nobodyhub.transcendence.zhihu.comment.service.ZhihuCommentApiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zhihu/comment")
public class ZhihuCommentController {
    private final ZhihuCommentApiService commentApiService;

    public ZhihuCommentController(ZhihuCommentApiService commentApiService) {
        this.commentApiService = commentApiService;
    }

    /**
     * Get comments of answer with given id
     *
     * @param answerId
     */
    @GetMapping(path = "/answer/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    void getAnswerComments(@PathVariable("answerId") String answerId) {
        commentApiService.getAnswerComments(answerId);
    }

    /**
     * Get comments of article with given id
     *
     * @param articleId
     */
    @GetMapping(path = "/article/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    void getArticleComments(@PathVariable("articleId") String articleId) {
        commentApiService.getArticleComments(articleId);
    }

    /**
     * Get comments of question with given id
     *
     * @param questionId
     */
    @GetMapping(path = "/question/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    void getQuestionComments(@PathVariable("questionId") String questionId) {
        commentApiService.getQuestionComments(questionId);
    }
}
