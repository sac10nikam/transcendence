package com.nobodyhub.transcendence.api.executor.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Member Employment information
 */
@Data
public class ZhihuApiEmployment {
    @JsonProperty("job")
    protected ZhihuApiTopic job;
    @JsonProperty("company")
    protected ZhihuApiTopic company;
}
