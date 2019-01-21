package com.nobodyhub.transcendence.hub.admin.controller;

import com.nobodyhub.transcendence.hub.admin.client.ZhihuTopicApiClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class ZhihuAdminController {

    private final ZhihuTopicApiClient zhihuTopicApiClient;

    public ZhihuAdminController(ZhihuTopicApiClient zhihuTopicApiClient) {
        this.zhihuTopicApiClient = zhihuTopicApiClient;
    }

    @GetMapping("/zhihu/refresh/topics/all")
    @ResponseStatus(HttpStatus.OK)
    public void refreshAllTopics() {
        zhihuTopicApiClient.refreshTopics(true);
    }

    @GetMapping("/zhihu/refresh/cookies")
    @ResponseStatus(HttpStatus.OK)
    public void refreshCookies() {
        zhihuTopicApiClient.refreshTopics(false);
    }


}
