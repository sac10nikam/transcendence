package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ZhihuAnswer.class, name = "answer"),
    @JsonSubTypes.Type(value = ZhihuArticle.class, name = "article"),
})
@Data
public abstract class ZhihuFeedContent {
    @JsonProperty("type")
    protected String type;
}
