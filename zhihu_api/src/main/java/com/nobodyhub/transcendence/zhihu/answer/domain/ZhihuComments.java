package com.nobodyhub.transcendence.zhihu.answer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiPaging;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;
import lombok.Data;

import java.util.List;

@Data
public class ZhihuComments {
    @JsonProperty("paging")
    private ZhihuApiPaging paging;
    @JsonProperty("data")
    private List<ZhihuComment> data;
}
