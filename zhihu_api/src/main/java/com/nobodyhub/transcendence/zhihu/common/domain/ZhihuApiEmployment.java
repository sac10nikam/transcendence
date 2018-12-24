package com.nobodyhub.transcendence.zhihu.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

/**
 * Member Employment information
 */
@Data
public class ZhihuApiEmployment implements Mergeable {
    @JsonProperty("job")
    protected ZhihuApiTopic job;
    @JsonProperty("company")
    protected ZhihuApiTopic company;
}
