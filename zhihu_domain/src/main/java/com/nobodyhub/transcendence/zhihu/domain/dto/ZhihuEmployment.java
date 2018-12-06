package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.nobodyhub.transcendence.zhihu.domain.common.Mergeable;
import lombok.Data;

@Data
public class ZhihuEmployment implements Mergeable {
    private ZhihuTopic job;
    private ZhihuTopic company;
}
