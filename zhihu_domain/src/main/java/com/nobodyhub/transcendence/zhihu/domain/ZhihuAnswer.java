package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

/**
 * Zhihu Answer from API
 */
@Data
public class ZhihuAnswer extends ZhihuFeedContent implements Mergeable {
    @JsonProperty("id")
    protected String id;
    @JsonProperty("question")
    protected ZhihuQuestion question;
    @JsonProperty("author")
    protected ZhihuMember author;
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
