package com.nobodyhub.transcendence.zhihu.topic.service;

import com.nobodyhub.transcendence.zhihu.api.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopicCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZhihuTopicApiServiceTest {
    @Autowired
    private ZhihuTopicApiService apiService;

    @Test
    public void getAllTopicsOfCategoryTest() {
        List<ZhihuTopic> topics = apiService.getTopicsByCategory(
            new ZhihuTopicCategory(1761, "生活方式"));
        assertTrue(topics.size() > 420);
    }

    @Test
    public void getAnswerByTopicTest() {
        List<ZhihuAnswer> answers = apiService.getAnswerByTopic("19551556");
        assertTrue(answers.size() > 100);
    }
}
