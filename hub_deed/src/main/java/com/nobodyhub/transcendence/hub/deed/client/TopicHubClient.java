package com.nobodyhub.transcendence.hub.deed.client;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuColumn;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hub-topic", path = "/topic")
public interface TopicHubClient {

    @PostMapping(path = "/zhihu-topic/get")
    Topic getByZhihuTopic(@RequestBody ZhihuTopic zhihuTopic);

    @PostMapping(path = "/zhihu-question/get")
    Topic getByZhihuQuestion(@RequestBody ZhihuQuestion zhihuQuestion);

    @PostMapping(path = "/zhihu-column/get")
    Topic getByZhihuColumn(@RequestBody ZhihuColumn zhihuColumn);
}
