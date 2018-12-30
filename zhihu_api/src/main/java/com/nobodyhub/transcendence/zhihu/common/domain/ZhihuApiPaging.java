package com.nobodyhub.transcendence.zhihu.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZhihuApiPaging {
    @JsonProperty("is_end")
    private Boolean isEnd;
    @JsonProperty("is_start")
    private Boolean isStart;
    @JsonProperty("next")
    private String next;
    @JsonProperty("previous")
    private String previous;
    @JsonProperty("total")
    private Long total;
}
