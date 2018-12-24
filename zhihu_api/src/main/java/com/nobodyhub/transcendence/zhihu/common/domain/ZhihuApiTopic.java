package com.nobodyhub.transcendence.zhihu.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

/**
 * A generic data structure to represent topic, like job, company. and etc.
 */
@Data
public class ZhihuApiTopic implements Mergeable {
    @JsonProperty("introduction")
    protected String introduction;
    @JsonProperty("avatar_url")
    protected String avatarUrl;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("url")
    protected String url;
    @JsonProperty("type")
    protected String type;
    @JsonProperty("excerpt")
    protected String excerpt;
    @JsonProperty("id")
    protected String id;
}
