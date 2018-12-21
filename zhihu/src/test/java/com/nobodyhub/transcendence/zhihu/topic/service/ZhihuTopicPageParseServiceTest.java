package com.nobodyhub.transcendence.zhihu.topic.service;

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
public class ZhihuTopicPageParseServiceTest {
    @Autowired
    private ZhihuTopicPageParseService parseService;

    @Test
    public void getAllCategoriesTest() {
        List<ZhihuTopicCategory> categories = parseService.getAllCategories();
        assertTrue(!categories.isEmpty());
    }
}
