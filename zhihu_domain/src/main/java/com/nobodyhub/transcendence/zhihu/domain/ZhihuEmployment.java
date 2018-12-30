package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

/**
 * Member Employment information
 */
@Data
public class ZhihuEmployment implements Mergeable {
    @JsonProperty("job")
    protected ZhihuTopic job;
    @JsonProperty("company")
    protected ZhihuTopic company;
}
