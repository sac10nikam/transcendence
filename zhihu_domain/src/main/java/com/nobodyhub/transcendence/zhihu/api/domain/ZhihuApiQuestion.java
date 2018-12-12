package com.nobodyhub.transcendence.zhihu.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
public class ZhihuApiQuestion implements Mergeable {

    @Id
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
    @JsonProperty("relationship")
    protected Object relationship;
}
