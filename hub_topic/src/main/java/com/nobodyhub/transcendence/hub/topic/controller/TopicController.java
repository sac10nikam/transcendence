package com.nobodyhub.transcendence.hub.topic.controller;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.controller.dto.TopicDtoConverter;
import com.nobodyhub.transcendence.hub.topic.controller.request.RequestUtil;
import com.nobodyhub.transcendence.hub.topic.controller.resp.TopicListResp;
import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private final HttpServletRequest httpRequest;
    private final TopicService topicService;

    private static final String PAGE_SIZE = "20";

    public TopicController(HttpServletRequest httpRequest,
                           TopicService topicService) {
        this.httpRequest = httpRequest;
        this.topicService = topicService;
    }

    @GetMapping("/search/name/{name}")
    TopicListResp searchByName(@PathVariable("name") String name,
                               @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                               @RequestParam(value = "size", required = false, defaultValue = PAGE_SIZE) int size) {

        Page<Topic> topics = topicService.findByName(name, PageRequest.of(page, size));
        return TopicListResp.of(
            RequestUtil.getPaging(httpRequest, topics),
            topics.getContent().stream().map(TopicDtoConverter::from).collect(Collectors.toList())
        );
    }

}
