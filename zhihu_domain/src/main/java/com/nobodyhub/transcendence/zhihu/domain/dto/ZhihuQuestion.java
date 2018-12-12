package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
public class ZhihuQuestion implements Mergeable {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;
    @JsonProperty("title")
    private String title;
    @JsonProperty("question_type")
    private String questionType;
    @JsonProperty("created")
    private String created;
    @JsonProperty("updated_time")
    private String updatedTime;
    @JsonProperty("url")
    private String url;
    @JsonProperty("relationship")
    private Object relationship;
}
