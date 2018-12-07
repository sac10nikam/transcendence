package com.nobodyhub.transcendence.zhihu.topic.service;

import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuAuthor;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * https://dzone.com/articles/microservices-communication-feign-as-rest-client
 */
@FeignClient(name = "zhihu_author", url = "localhost")
public interface ZhihuAuthorClient {
    @PostMapping
    ZhihuAuthor save(@RequestBody ZhihuAuthor author);
}
