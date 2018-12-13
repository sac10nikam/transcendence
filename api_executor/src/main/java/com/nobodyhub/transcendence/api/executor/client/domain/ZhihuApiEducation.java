package com.nobodyhub.transcendence.api.executor.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Member Education information
 */
@Data
public class ZhihuApiEducation {
    @JsonProperty("major")
    protected ZhihuApiTopic major;
    @JsonProperty("school")
    protected ZhihuApiTopic school;
    @JsonProperty("diploma")
    protected int diploma;
}
