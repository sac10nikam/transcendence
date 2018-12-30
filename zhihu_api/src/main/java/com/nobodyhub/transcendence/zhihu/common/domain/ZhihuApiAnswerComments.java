package com.nobodyhub.transcendence.zhihu.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ZhihuApiAnswerComments {
    @JsonProperty("paging")
    private ZhihuApiPaging paging;
    @JsonProperty("data")
    private List<ZhihuApiAnswerComment> data;
}
