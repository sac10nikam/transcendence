package com.nobodyhub.transcendence.zhihu.topic.domain.feed;

import com.nobodyhub.transcendence.zhihu.topic.domain.paging.ZhihuTopicPaging;
import lombok.Data;

import java.util.List;

/**
 * Data Structure returned by https://www.zhihu.com/api/v4/topics/{id}/feeds/essence
 */
@Data
public class ZhihuTopicFeedList {
    private List<ZhihuTopicFeed> data;
    private ZhihuTopicPaging paging;
}
