package com.nobodyhub.transcendence.zhihu.member.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaSourceTest {
    @Autowired
    private KafkaSource kafkaSource;

    @Test
    public void getByUrlTokenTest() {
        this.kafkaSource.getByUrlToken("ohgee");
    }

}
