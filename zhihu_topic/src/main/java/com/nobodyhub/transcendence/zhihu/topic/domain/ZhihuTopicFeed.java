package com.nobodyhub.transcendence.zhihu.topic.domain;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import lombok.Data;

@Data
public class ZhihuTopicFeed {
    private String attached_info;
    private String type;
    private ZhihuAnswer target;
}
