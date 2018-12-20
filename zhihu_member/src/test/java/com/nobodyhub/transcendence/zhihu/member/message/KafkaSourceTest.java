package com.nobodyhub.transcendence.zhihu.member.message;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaSourceTest {
    @Autowired
    private KafkaSource kafkaSource;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void getByUrlTokenTest() {
        this.kafkaSource.getByUrlToken("ohgee");
    }
}
