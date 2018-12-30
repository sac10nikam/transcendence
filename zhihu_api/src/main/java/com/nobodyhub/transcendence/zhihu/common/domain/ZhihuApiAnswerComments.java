package com.nobodyhub.transcendence.zhihu.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.zhihu.question.domain.ZhihuPaging;
import lombok.Data;

import java.util.List;

@Data
public class ZhihuApiAnswerComments {
    @JsonProperty("paging")
    private ZhihuPaging paging;
    @JsonProperty("data")
    private List<ZhihuApiAnswerComment> data;
}
