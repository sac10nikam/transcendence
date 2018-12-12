package com.nobodyhub.transcendence.zhihu.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

@Data
public class ZhihuApiEmployment implements Mergeable {
    @JsonProperty("job")
    protected ZhihuApiTopic job;
    @JsonProperty("company")
    protected ZhihuApiTopic company;
}
