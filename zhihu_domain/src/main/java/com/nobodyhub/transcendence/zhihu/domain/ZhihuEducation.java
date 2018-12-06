package com.nobodyhub.transcendence.zhihu.domain;

import lombok.Data;

@Data
public class ZhihuEducation {
    private ZhihuTopic major;
    private ZhihuTopic school;
    private int diploma;
}
