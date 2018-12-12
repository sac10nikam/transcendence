package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

/**
 * A generic datastructure to represent topic, like job, company. and etc.
 */
@Data
public class ZhihuTopic implements Mergeable {
    @JsonProperty("introduction")
    private String introduction;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("type")
    private String type;
    @JsonProperty("excerpt")
    private String excerpt;
    @JsonProperty("id")
    private String id;
}
