package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.zhihu.domain.merge.Mergeable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class ZhihuAnswer implements Mergeable {
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;
    @JsonProperty("question")
    private ZhihuQuestion question;
    @JsonProperty("author")
    private ZhihuAuthor author;
    @JsonProperty("url")
    private String url;
    @JsonProperty("created_time")
    private long createdTime;
    @JsonProperty("updated_time")
    private long updatedTime;
    @JsonProperty("voteup_count")
    private long voteupCount;
    @JsonProperty("comment_count")
    private long commentCount;
    @JsonProperty("content")
    private String content;
    @JsonProperty("excerpt")
    private String excerpt;
}
