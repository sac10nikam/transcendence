package com.nobodyhub.transcendence.fetcher.service;

import com.nobodyhub.transcendence.fetcher.IntegrationTest;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuMember;
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
    public void getAnswersForMemberTest() {
        List<ZhihuAnswer> answerList = this.zhihuApiService.getAnswersForMember("yun-dong-shan");
        assertNotNull(answerList);
        assertEquals("yun-dong-shan", answerList.get(0).getAuthor().getUrl_token());
    }

    @Test
    public void getAuthorTest() {
        ZhihuMember author = zhihuApiService.getAuthor("zhang-wei-21-56-86");
        assertNotNull(author);
        assertEquals("zhang-wei-21-56-86", author.getUrl_token());
    }
}
