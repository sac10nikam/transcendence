package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.domain.TopicData;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

/**
 * Zhihu Question
 */
@Data
public class ZhihuQuestion implements Mergeable, TopicData {
    @JsonProperty("id")
    protected String id;
    @JsonProperty("type")
    protected String type;
    @JsonProperty("title")
    protected String title;
    @JsonProperty("question_type")
    protected String questionType;
    @JsonProperty("created")
    protected String created;
    @JsonProperty("updated_time")
    protected String updatedTime;
    @JsonProperty("url")
    protected String url;
}
