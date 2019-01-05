package com.nobodyhub.transcendence.zhihu.article.controller;


import com.nobodyhub.transcendence.zhihu.article.service.ZhihuArticleApiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zhihu/article")
public class ZhihuArticleController {
    private final ZhihuArticleApiService articleApiService;

    public ZhihuArticleController(ZhihuArticleApiService articleApiService) {
        this.articleApiService = articleApiService;
    }

    @GetMapping(path = "/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    void getArticleById(String articleId) {
        articleApiService.getById(articleId);
    }

    @GetMapping(path = "/columnId/{columnId}")
    @ResponseStatus(HttpStatus.OK)
    void getArticleByColumnId(String columnId) {
        articleApiService.getByColumnId(columnId);
    }
}
