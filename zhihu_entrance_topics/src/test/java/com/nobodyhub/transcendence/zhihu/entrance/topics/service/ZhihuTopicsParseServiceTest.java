package com.nobodyhub.transcendence.zhihu.entrance.topics.service;

import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopicCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZhihuTopicsParseServiceTest {
    @Autowired
    private ZhihuTopicsParseService parseService;

    @Test
    public void getAllCategoriesTest() {
        List<ZhihuTopicCategory> categories = parseService.getAllCategories();
        assertTrue(!categories.isEmpty());
    }

    @Test
    public void getAllTopicsOfCategoryTest() {
        List<ZhihuTopic> topics = parseService.getAllTopicsOfCategory(
            new ZhihuTopicCategory(1761, "生活方式"));
        assertTrue(topics.size() > 420);
    }

}
