package com.nobodyhub.transcendence.hub.deed.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "zhihu-api", path = "/zhihu/article")
public interface ZhihuArticleApiClient {
    @GetMapping(path = "/id/{id}")
    void getArticleById(String articleId);

    @GetMapping(path = "/columnId/{columnId}")
    void getArticleByColumnId(String columnId);
}
