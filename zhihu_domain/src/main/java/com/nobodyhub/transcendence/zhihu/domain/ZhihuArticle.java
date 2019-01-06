package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

import java.util.List;

@Data
public class ZhihuArticle extends ZhihuFeedContent implements Mergeable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("excerpt_title")
    private String excerptTitle;
    @JsonProperty("excerpt")
    private String excerpt;
    @JsonProperty("author")
    private ZhihuMember author;
    @JsonProperty("column")
    private ZhihuColumn column;
    @JsonProperty("comment_count")
    private Integer comment_count;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("url")
    private String url;
    @JsonProperty("topic_thumbnails")
    private List<String> topicThumbnails;
    @JsonProperty("voteup_count")
    private Integer voteup_count;
    @JsonProperty("created")
    private Long created;
    @JsonProperty("updated")
    private Long updated;

    /**
     * Plain text contents of the article
     * Not returned by API, parsed from HTML page
     */
    private String contentText;

    /**
     * Rich text contents of the article
     * Not returned by API, parsed from HTML page
     */
    private String contentHtml;
}
