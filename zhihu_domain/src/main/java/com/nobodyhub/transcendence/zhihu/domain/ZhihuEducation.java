package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

/**
 * Member Education information
 */
@Data
public class ZhihuEducation implements Mergeable {
    @JsonProperty("major")
    protected ZhihuTopic major;
    @JsonProperty("school")
    protected ZhihuTopic school;
    @JsonProperty("diploma")
    protected int diploma;
}
