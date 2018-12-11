package com.nobodyhub.transcendence.zhihu.question.service;

import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuMember;
import com.nobodyhub.transcendence.zhihu.question.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Category(IntegrationTest.class)
public class ZhihuQuestionApiServiceImplTest {

    @Autowired
    private ZhihuQuestionApiService zhihuQuestionApiService;

    @Test
    public void getAnswersForQuestionTest() {
        List<ZhihuAnswer> answerList = this.zhihuQuestionApiService.getAnswersForQuestion("49472046");
        assertNotNull(answerList);
        assertEquals("49472046", answerList.get(0).getQuestion().getId());
    }

    @Test
    public void getAnswersForMemberTest() {
        List<ZhihuAnswer> answerList = this.zhihuQuestionApiService.getAnswersForMember("yun-dong-shan");
        assertNotNull(answerList);
        assertEquals("yun-dong-shan", answerList.get(0).getAuthor().getUrlToken());
    }

    @Test
    public void getAuthorTest() {
        ZhihuMember author = zhihuQuestionApiService.getAuthor("zhang-wei-21-56-86");
        assertNotNull(author);
        assertEquals("zhang-wei-21-56-86", author.getUrlToken());
    }
}
