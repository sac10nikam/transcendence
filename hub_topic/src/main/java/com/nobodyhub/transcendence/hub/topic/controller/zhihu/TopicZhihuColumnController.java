package com.nobodyhub.transcendence.hub.topic.controller.zhihu;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuColumn;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for Zhihu Column
 */
@RestController
@RequestMapping("/topics/zhihu/column")
public class TopicZhihuColumnController {

    private final TopicService topicService;

    public TopicZhihuColumnController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Get by zhihu column
     *
     * @param zhihuColumn
     */
    @PostMapping(path = "/get")
    Topic getByZhihuColumn(@RequestBody ZhihuColumn zhihuColumn) {
        return topicService.find(zhihuColumn);
    }

    /**
     * Save zhihu column
     *
     * @param zhihuColumn
     */
    @PostMapping(path = "/save")
    Topic saveZhihuQuestion(@RequestBody ZhihuColumn zhihuColumn) {
        return topicService.save(zhihuColumn);
    }

    /**
     * Save zhihu column without return value
     *
     * @param zhihuColumn
     */
    @PostMapping(path = "/save/no-return")
    @ResponseStatus(HttpStatus.OK)
    void saveZhihuQuestionNoReturn(@RequestBody ZhihuColumn zhihuColumn) {
        topicService.save(zhihuColumn);
    }
}
