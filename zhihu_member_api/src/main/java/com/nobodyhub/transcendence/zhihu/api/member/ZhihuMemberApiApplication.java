package com.nobodyhub.transcendence.zhihu.api.member;

import com.nobodyhub.transcendence.zhihu.api.common.ZhihuApiConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(ZhihuApiConfiguration.class)
@SpringBootApplication
public class ZhihuMemberApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhihuMemberApiApplication.class, args);
    }

}

