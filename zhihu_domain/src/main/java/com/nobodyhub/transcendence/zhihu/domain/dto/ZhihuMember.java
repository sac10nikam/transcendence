package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.zhihu.domain.merge.Mergeable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor(force = true)
public class ZhihuMember implements Mergeable {
    @Id
    @JsonProperty("id")
    private String id;

    @Indexed(unique = true)
    @JsonProperty("url_token")
    private String urlToken;

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("is_active")
    private Integer isActive;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("avatar_url_template")
    private String avatarUrlTemplate;
    @JsonProperty("is_org")
    private Boolean isOrg;
    @JsonProperty("type")
    private String type;
    @JsonProperty("url")
    private String url;
    @JsonProperty("user_type")
    private String userType;
    @JsonProperty("headline")
    private String headline;
    @JsonProperty("gender")
    private Integer gender;
    @JsonProperty("is_advertiser")
    private Boolean isAdvertiser;
    @JsonProperty("follower_count")
    private Long followerCount;
    @JsonProperty("following_count")
    private Long followingCount;
    @JsonProperty("answer_count")
    private Long answerCount;
    @JsonProperty("articles_count")
    private Long articlesCount;
    @JsonProperty("voteup_count")
    private Long voteupCount;
    @JsonProperty("vote_to_count")
    private Long voteToCount;
    @JsonProperty("commercial_question_count")
    private Long commercialQuestionCount;
    @JsonProperty("question_count")
    private Long questionCount;
    @JsonProperty("following_question_count")
    private Long followingQuestionCount;
    @JsonProperty("participated_live_count")
    private Long participatedLiveCount;
    @JsonProperty("following_favlists_count")
    private Long followingFavlistsCount;
    @JsonProperty("favorited_count")
    private Long favoritedCount;
    @JsonProperty("following_topic_count")
    private Long followingTopicCount;
    @JsonProperty("thank_to_count")
    private Long thankToCount;
    @JsonProperty("thank_from_count")
    private Long thankFromCount;
    @JsonProperty("thanked_count")
    private Long thankedCount;
    @JsonProperty("business")
    private ZhihuTopic business;
    @JsonProperty("employments")
    private List<ZhihuEmployment> employments;
    @JsonProperty("educations")
    private List<ZhihuEducation> educations;
    @JsonProperty("locations")
    private List<ZhihuTopic> locations;
}
