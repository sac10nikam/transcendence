package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;

@Data
public class ZhihuColumn implements Mergeable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("author")
    private ZhihuMember author;
    @JsonProperty("url")
    private String url;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("followers")
    private Long followers;
    @JsonProperty("articles_count")
    private Integer articlesCount;
    @JsonProperty("updated")
    private Long updated;
}
