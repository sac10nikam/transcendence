package com.nobodyhub.transcendence.zhihu.topic.client;

import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuQuestion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zhihu-question", path = "/questions")
public interface ZhihuQuestionClient {
    @PostMapping
    ZhihuQuestion save(@RequestBody ZhihuQuestion question);
}
