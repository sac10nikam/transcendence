package com.nobodyhub.transcendence.zhihu.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

/**
 * Member Education information
 */
@Data
public class ZhihuApiEducation implements Mergeable {
    @JsonProperty("major")
    protected ZhihuApiTopic major;
    @JsonProperty("school")
    protected ZhihuApiTopic school;
    @JsonProperty("diploma")
    protected int diploma;
}
