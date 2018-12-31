package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ZhihuComment {
    @JsonProperty("id")
    private String id;
    @JsonProperty("author")
    private ZhihuAuthor author;
    @JsonProperty("child_comments")
    private List<ZhihuComment> childComments;
    @JsonProperty("content")
    private String content;
    @JsonProperty("created_time")
    private Long createdTime;
    @JsonProperty("reply_to_author")
    private ZhihuAuthor replyToAuthor;
    @JsonProperty("url")
    private String url;
    @JsonProperty("vote_count")
    private Long voteCount;
}
