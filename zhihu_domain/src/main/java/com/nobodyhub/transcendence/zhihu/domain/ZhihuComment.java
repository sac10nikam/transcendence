package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

import java.util.List;

@Data
public class ZhihuComment implements Mergeable {
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

    /**
     * id of parent content, can be either answer, question, article
     */
    private String parentId;
}
