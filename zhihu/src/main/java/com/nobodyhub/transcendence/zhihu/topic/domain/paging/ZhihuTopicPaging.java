package com.nobodyhub.transcendence.zhihu.topic.domain.paging;

import lombok.Data;

@Data
public class ZhihuTopicPaging {
    private Boolean is_end;
    private Boolean is_start;
    private String next;
    private String previous;
}
