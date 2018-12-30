package com.nobodyhub.transcendence.zhihu.topic.domain.paging;


import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiPaging;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import lombok.Data;

import java.util.List;

@Data
public class ZhihuTopicList {
    private List<ZhihuTopic> data;
    private ZhihuApiPaging paging;
}
