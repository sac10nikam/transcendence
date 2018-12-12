package com.nobodyhub.transcendence.zhihu.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class ZhihuApiAnswer implements Mergeable {
    @Id
    @JsonProperty("id")
    protected String id;

    @JsonProperty("type")
    protected String type;
    @JsonProperty("question")
    protected ZhihuApiQuestion question;
    @JsonProperty("author")
    protected ZhihuApiMember author;
    @JsonProperty("url")
    protected String url;
    @JsonProperty("created_time")
    protected long createdTime;
    @JsonProperty("updated_time")
    protected long updatedTime;
    @JsonProperty("voteup_count")
    protected long voteupCount;
    @JsonProperty("comment_count")
    protected long commentCount;
    @JsonProperty("content")
    protected String content;
    @JsonProperty("excerpt")
    protected String excerpt;
}
