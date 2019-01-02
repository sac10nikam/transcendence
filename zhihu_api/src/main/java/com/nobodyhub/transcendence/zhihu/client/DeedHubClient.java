package com.nobodyhub.transcendence.zhihu.client;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "hub-deed", path = "/deeds")
public interface DeedHubClient {
    @PostMapping("/zhihu-answer/save/no-return")
    void saveZhihuAnswerNoReturn(ZhihuAnswer zhihuAnswer);

    @PostMapping("/zhihu-comment/save/no-return")
    void saveZhihuCommentNoReturn(ZhihuComment zhihuComment);
}
