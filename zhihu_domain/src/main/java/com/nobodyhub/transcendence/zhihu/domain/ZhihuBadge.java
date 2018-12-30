package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZhihuBadge {
    @JsonProperty("description")
    private String description;
    @JsonProperty("type")
    private String type;
}
