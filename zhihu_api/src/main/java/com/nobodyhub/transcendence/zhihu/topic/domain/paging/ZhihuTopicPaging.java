package com.nobodyhub.transcendence.zhihu.topic.domain.paging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZhihuTopicPaging {
    @JsonProperty("is_end")
    private Boolean isEnd;
    @JsonProperty("is_start")
    private Boolean isStart;
    @JsonProperty("next")
    private String next;
    @JsonProperty("previous")
    private String previous;
}
