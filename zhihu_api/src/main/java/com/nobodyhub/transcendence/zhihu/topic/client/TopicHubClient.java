package com.nobodyhub.transcendence.zhihu.topic.client;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Routing client for TopicController in hub-topic
 */
@FeignClient(name = "hub-topic", path = "/topics")
public interface TopicHubClient {
    @PostMapping(path = "/zhihu")
    void saveZhihuTopic(ZhihuTopic zhihuTopic);
}
