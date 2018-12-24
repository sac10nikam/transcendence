package com.nobodyhub.transcendence.zhihu.topic.domain.feed;

import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiAnswer;
import lombok.Data;

@Data
public class ZhihuTopicFeed {
    private String attached_info;
    private String type;
    private ZhihuApiAnswer target;
}
