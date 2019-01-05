package com.nobodyhub.transcendence.zhihu.common.client;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuArticle;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "hub-deed", path = "/deeds")
public interface DeedHubClient {
    @Async
    @PostMapping("/zhihu-answer/save/no-return")
    void saveZhihuAnswerNoReturn(ZhihuAnswer zhihuAnswer);

    @Async
    @PostMapping("/zhihu-comment/save/no-return")
    void saveZhihuCommentNoReturn(ZhihuComment zhihuComment);

    @Async
    @PostMapping("/zhihu-article/save/no-return")
    void saveZhihuArticleNoReturn(ZhihuArticle zhihuArticle);
}
