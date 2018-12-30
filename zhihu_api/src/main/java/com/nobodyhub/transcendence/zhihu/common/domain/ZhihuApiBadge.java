package com.nobodyhub.transcendence.zhihu.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZhihuApiBadge {
    @JsonProperty("description")
    private String description;
    @JsonProperty("type")
    private String type;
}
