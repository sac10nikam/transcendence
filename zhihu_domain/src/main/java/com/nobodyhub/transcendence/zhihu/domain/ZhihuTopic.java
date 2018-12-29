package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ZhihuTopic implements Mergeable {
    /**
     * Unique id in zhihu topics
     */
    @JsonProperty("id")
    private String id;
    /**
     * Should be the same with enclosing Topic#name
     */
    @JsonProperty("name")
    private String name;
    @JsonProperty("introduction")
    private String introduction;
    @JsonProperty("excerpt")
    private String excerpt;
    @JsonProperty("url")
    private String url;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("unanswered_count")
    private Long unansweredCount;
    @JsonProperty("bestAnswerersCount")
    private Long best_answerers_count;
    @JsonProperty("questionsCount")
    private Long questions_count;
    @JsonProperty("followersCount")
    private Long followers_count;
    @JsonProperty("bestAnswersCount")
    private Long best_answers_count;

    private Set<ZhihuTopic> parents;
    private Set<ZhihuTopic> children;
}
