package com.nobodyhub.transcendence.zhihu.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import lombok.Data;

/**
 * Zhihu Answer from API
 */
@Data
public class ZhihuApiAnswer implements Mergeable {
    @JsonProperty("id")
    protected String id;

    @JsonProperty("type")
    protected String type;
    @JsonProperty("question")
    protected ZhihuApiQuestion question;
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
