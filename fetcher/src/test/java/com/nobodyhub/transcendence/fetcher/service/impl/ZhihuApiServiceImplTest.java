package com.nobodyhub.transcendence.fetcher.service.impl;

import com.nobodyhub.transcendence.fetcher.IntegrationTest;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuAuthor;
import com.nobodyhub.transcendence.fetcher.service.ZhihuApiService;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Category(IntegrationTest.class)
public class ZhihuApiServiceImplTest {

    @Autowired
    private ZhihuApiService zhihuApiService;

    @Test
    public void getAnswersForQuestionTest() {
        List<ZhihuAnswer> answerList = this.zhihuApiService.getAnswersForQuestion("49472046");
        assertNotNull(answerList);
        assertEquals("49472046", answerList.get(0).getQuestion().getId());
    }

    @Test
    public void getAuthorTest() {
        ZhihuAuthor author = zhihuApiService.getAuthor("zhang-wei-21-56-86");
        assertNotNull(author);
        assertEquals("zhang-wei-21-56-86", author.getUrl_token());
    }
}
