package com.nobodyhub.transcendence.hub.deed.client;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@FeignClient(name = "hub-topic", path = "/topic")
public interface TopicHubController {

    @PostMapping(path = "/zhihu-topic")
    void saveZhihuTopic(@RequestBody ZhihuTopic zhihuTopic);

    @PostMapping(path = "/zhihu-topic/parent/{topicId}")
    void saveZhihuParentsTopic(@PathVariable("topicId") String topicId,
                               @RequestBody Set<ZhihuTopic> parents);

    @PostMapping(path = "/zhihu-topic/children/{topicId}")
    void saveZhihuChildrenTopic(@PathVariable("topicId") String topicId,
                                @RequestBody Set<ZhihuTopic> children);
}
