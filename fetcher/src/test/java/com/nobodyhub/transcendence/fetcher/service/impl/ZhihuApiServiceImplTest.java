package com.nobodyhub.transcendence.fetcher.service.impl;

import com.nobodyhub.transcendence.fetcher.domain.ZhihuAuthor;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuQuestion;
import com.nobodyhub.transcendence.fetcher.service.ZhihuApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZhihuApiServiceImplTest {
    @Autowired
    private ZhihuApiService zhihuApiService;

    @Test
    public void getAnswersForQuestionTest() {
        ZhihuQuestion question = this.zhihuApiService.getAnswersForQuestion("297528211", 0);
        //TODO: verify
    }

    @Test
    public void getAuthorTest() {
        ZhihuAuthor author = zhihuApiService.getAuthor("zhang-wei-21-56-86");
        assertNotNull(author);
        assertEquals("zhang-wei-21-56-86", author.getUrl_token());
        assertNotNull(author.getId());
        assertNotNull(author.getName());
        //TODO: check other fields are not null
    }
}
