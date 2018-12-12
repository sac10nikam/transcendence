package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

@Data
public class ZhihuEmployment implements Mergeable {
    @JsonProperty("job")
    private ZhihuTopic job;
    @JsonProperty("company")
    private ZhihuTopic company;
}
