package com.nobodyhub.transcendence.hub.topic.controller;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;

    private static final int PAGE_SIZE = 20;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/search")
    List<Topic> searchByName(@RequestBody SearchParam searchParam) {
        return topicService.findByName(searchParam.getName(),
            PageRequest.of(searchParam.getPage(), searchParam.getSize()));
    }

    @Data
    private static class SearchParam {
        /**
         * topic name
         */
        private String name;
        /**
         * page number to be queried
         */
        private int page = 0;
        /**
         * the page size to be queried
         */
        private int size = PAGE_SIZE;

    }
}
