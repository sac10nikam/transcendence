package com.nobodyhub.transcendence.fetcher.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZhihuUrlConvertServiceTest {
    @Autowired
    private ZhihuUrlConvertService convertService;

    @Test
    public void convertTest() {
        String newUrl = convertService.convert("/api/v4/members/{urlToken}?include=allow_message,is_followed,is_following,is_org,is_blocking,employments,answer_count,follower_count,articles_count,gender,badge[*].topics");
        assertEquals("/members/{urlToken}?include=allow_message,is_followed,is_following,is_org,is_blocking,employments,answer_count,follower_count,articles_count,gender,badge[*].topics",
            newUrl);

        newUrl = convertService.convert("/members/{urlToken}?include=allow_message,is_followed,is_following,is_org,is_blocking,employments,answer_count,follower_count,articles_count,gender,badge[*].topics");
        assertEquals("/members/{urlToken}?include=allow_message,is_followed,is_following,is_org,is_blocking,employments,answer_count,follower_count,articles_count,gender,badge[*].topics",
            newUrl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertTest_null() {
        convertService.convert(null);
    }
}
