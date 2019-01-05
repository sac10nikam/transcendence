package com.nobodyhub.transcendence.zhihu.common.client;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Routing client for TopicController in hub-topic
 */
@FeignClient(name = "hub-topic", path = "/topics")
public interface TopicHubClient {
    @Async
    @PostMapping(path = "/zhihu-topic/save/no-return")
    void saveZhihuTopicNoReturn(@RequestBody ZhihuTopic zhihuTopic);

    @Async
    @PostMapping(path = "/zhihu-question/save/no-return")
    void saveZhihuQuestionNoReturn(@RequestBody ZhihuQuestion zhihuQuestion);
}
