package com.nobodyhub.transcendence.zhihu.topic.domain.feed;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuFeedContent;
import lombok.Data;

@Data
public class ZhihuTopicFeed {
    private String attached_info;
    private String type;
    private ZhihuFeedContent target;
}
