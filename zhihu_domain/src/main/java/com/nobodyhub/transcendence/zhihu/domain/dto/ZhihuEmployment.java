package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.nobodyhub.transcendence.zhihu.domain.merge.Mergeable;
import lombok.Data;

@Data
public class ZhihuEmployment implements Mergeable {
    private ZhihuTopic job;
    private ZhihuTopic company;
}
