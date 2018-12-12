package com.nobodyhub.transcendence.zhihu.topic.domain.feed;

import com.nobodyhub.transcendence.zhihu.api.domain.ZhihuAnswer;
import lombok.Data;

@Data
public class ZhihuTopicFeed {
    private String attached_info;
    private String type;
    private ZhihuAnswer target;
}
