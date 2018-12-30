package com.nobodyhub.transcendence.zhihu.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ZhihuApiAnswerComment {
    @JsonProperty("id")
    private String id;
    @JsonProperty("author")
    private ZhihuApiAuthor author;
    @JsonProperty("child_comments")
    private List<ZhihuApiAnswerComment> childComments;
    @JsonProperty("content")
    private String content;
    @JsonProperty("created_time")
    private Long createdTime;
    @JsonProperty("reply_to_author")
    private ZhihuApiAuthor replyToAuthor;
    @JsonProperty("url")
    private String url;
    @JsonProperty("vote_count")
    private Long voteCount;
}
