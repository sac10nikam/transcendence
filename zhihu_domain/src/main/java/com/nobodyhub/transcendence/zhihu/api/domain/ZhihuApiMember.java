package com.nobodyhub.transcendence.zhihu.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Member of Zhihu, returned by member/ interface.
 * Knowned as Author as well
 */
@Document
@Data
@NoArgsConstructor(force = true)
public class ZhihuApiMember implements Mergeable {
    @Id
    @JsonProperty("id")
    protected String id;

    @Indexed(unique = true)
    @JsonProperty("url_token")
    protected String urlToken;

    @JsonProperty("name")
    protected String name;
    @JsonProperty("description")
    protected String description;
    @JsonProperty("is_active")
    protected Integer isActive;
    @JsonProperty("avatar_url")
    protected String avatarUrl;
    @JsonProperty("avatar_url_template")
    protected String avatarUrlTemplate;
    @JsonProperty("is_org")
    protected Boolean isOrg;
    @JsonProperty("type")
    protected String type;
    @JsonProperty("url")
    protected String url;
    @JsonProperty("user_type")
    protected String userType;
    @JsonProperty("headline")
    protected String headline;
    @JsonProperty("gender")
    protected Integer gender;
    @JsonProperty("is_advertiser")
    protected Boolean isAdvertiser;
    @JsonProperty("follower_count")
    protected Long followerCount;
    @JsonProperty("following_count")
    protected Long followingCount;
    @JsonProperty("answer_count")
    protected Long answerCount;
    @JsonProperty("articles_count")
    protected Long articlesCount;
    @JsonProperty("voteup_count")
    protected Long voteupCount;
    @JsonProperty("vote_to_count")
    protected Long voteToCount;
    @JsonProperty("commercial_question_count")
    protected Long commercialQuestionCount;
    @JsonProperty("question_count")
    protected Long questionCount;
    @JsonProperty("following_question_count")
    protected Long followingQuestionCount;
    @JsonProperty("participated_live_count")
    protected Long participatedLiveCount;
    @JsonProperty("following_favlists_count")
    protected Long followingFavlistsCount;
    @JsonProperty("favorited_count")
    protected Long favoritedCount;
    @JsonProperty("following_topic_count")
    protected Long followingTopicCount;
    @JsonProperty("thank_to_count")
    protected Long thankToCount;
    @JsonProperty("thank_from_count")
    protected Long thankFromCount;
    @JsonProperty("thanked_count")
    protected Long thankedCount;
    @JsonProperty("business")
    protected ZhihuApiTopic business;
    @JsonProperty("employments")
    protected List<ZhihuApiEmployment> employments;
    @JsonProperty("educations")
    protected List<ZhihuApiEducation> educations;
    @JsonProperty("locations")
    protected List<ZhihuApiTopic> locations;
}
