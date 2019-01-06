package com.nobodyhub.transcendence.hub.deed.controller;

import com.nobodyhub.transcendence.hub.deed.service.DeedService;
import com.nobodyhub.transcendence.hub.domain.Deed;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuArticle;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/deeds")
public class DeedController {
    private final DeedService deedService;

    public DeedController(DeedService deedService) {
        this.deedService = deedService;
    }

    @PostMapping("/zhihu-answer/save")
    Deed saveZhihuAnswer(@RequestBody ZhihuAnswer zhihuAnswer) {
        return this.deedService.save(zhihuAnswer);
    }

    @PostMapping("/zhihu-answer/save/no-return")
    @ResponseStatus(OK)
    void saveZhihuAnswerNoReturn(@RequestBody ZhihuAnswer zhihuAnswer) {
        this.deedService.save(zhihuAnswer);
    }

    @PostMapping("/zhihu-answer/get")
    Deed getByZhihuAnswer(@RequestBody ZhihuAnswer zhihuAnswer) {
        return this.deedService.find(zhihuAnswer);
    }

    @GetMapping("/zhihu-answer/{answerId}")
    ResponseEntity<Deed> getByZhihuAnswerById(@PathVariable("answerId") String answerId) {
        Optional<Deed> deed = this.deedService.findByZhihuAnswerId(answerId);
        return deed.map(value -> new ResponseEntity<>(value, OK))
            .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }


    @PostMapping("/zhihu-comment/save")
    Deed saveZhihuComment(ZhihuComment zhihuComment) {
        return this.deedService.save(zhihuComment);
    }

    @PostMapping("/zhihu-comment/save/no-return")
    @ResponseStatus(OK)
    void saveZhihuCommentNoReturn(ZhihuComment zhihuComment) {
        this.deedService.save(zhihuComment);
    }

    @PostMapping("/zhihu-comment/get")
    Deed getByZhihuComment(ZhihuComment zhihuComment) {
        return this.deedService.find(zhihuComment);
    }

    @GetMapping("/zhihu-comment/{commentId}")
    ResponseEntity<Deed> getByZhihuCommentById(@PathVariable("commentId") String commentId) {
        Optional<Deed> deed = this.deedService.findByZhihuCommentId(commentId);
        return deed.map(value -> new ResponseEntity<>(value, OK))
            .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @PostMapping("/zhihu-article/save")
    Deed saveZhihuArticle(ZhihuArticle zhihuArticle) {
        return this.deedService.save(zhihuArticle);
    }

    @PostMapping("/zhihu-article/save/no-return")
    @ResponseStatus(OK)
    void saveZhihuArticleNoReturn(ZhihuArticle zhihuArticle) {
        this.deedService.save(zhihuArticle);
    }

    @PostMapping("/zhihu-article/get")
    Deed getByZhihuArticle(ZhihuArticle zhihuArticle) {
        return this.deedService.find(zhihuArticle);
    }

    @GetMapping("/zhihu-article/{articleId}")
    ResponseEntity<Deed> getByZhihuArticletById(@PathVariable("articleId") String articleId) {
        Optional<Deed> deed = this.deedService.findByZhihuArticleId(articleId);
        return deed.map(value -> new ResponseEntity<>(value, OK))
            .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }
}
