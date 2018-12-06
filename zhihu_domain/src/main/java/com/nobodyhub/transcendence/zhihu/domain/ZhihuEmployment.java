package com.nobodyhub.transcendence.zhihu.domain;

import lombok.Data;

@Data
public class ZhihuEmployment implements Mergeable {
    private ZhihuTopic job;
    private ZhihuTopic company;
}
