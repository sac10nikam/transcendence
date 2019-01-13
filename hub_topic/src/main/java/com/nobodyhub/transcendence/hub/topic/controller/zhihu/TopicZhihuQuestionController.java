package com.nobodyhub.transcendence.hub.topic.controller.zhihu;

import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for Zhihu Question
 */
@RestController
@RequestMapping("/topics/zhihu/question")
public class TopicZhihuQuestionController {

    private final TopicService topicService;

    public TopicZhihuQuestionController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Get by zhihu question
     *
     * @param zhihuQuestion
     */
    @PostMapping(path = "/get")
    Topic getByZhihuQuestion(@RequestBody ZhihuQuestion zhihuQuestion) {
        return topicService.find(zhihuQuestion);
    }

    /**
     * Save zhihu question
     *
     * @param zhihuQuestion
     */
    @PostMapping(path = "/save")
    Topic saveZhihuQuestion(@RequestBody ZhihuQuestion zhihuQuestion) {
        return topicService.save(zhihuQuestion);
    }

    /**
     * Save zhihu question without return value
     *
     * @param zhihuQuestion
     */
    @PostMapping(path = "/save/no-return")
    @ResponseStatus(HttpStatus.OK)
    void saveZhihuQuestionNoReturn(@RequestBody ZhihuQuestion zhihuQuestion) {
        topicService.save(zhihuQuestion);
    }
}
