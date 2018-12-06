package com.nobodyhub.transcendence.zhihu.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
public class ZhihuAuthor {
    private int is_active;
    private String id;
    private String url_token;
    private String name;
    private String description;
    private String avatar_url;
    private String avatar_url_template;
    private Boolean is_org;
    private String type;
    private String url;
    private String user_type;
    private String headline;
    private int gender;
    private Boolean is_advertiser;
    private Long follower_count;
    private Long following_count;
    private Long answer_count;
    private Long articles_count;
    private Long voteup_count;
    private Long vote_to_count;
    private Long commercial_question_count;
    private Long question_count;
    private Long following_question_count;
    private Long participated_live_count;
    private Long following_favlists_count;
    private Long favorited_count;
    private Long following_topic_count;
    private Long thank_to_count;
    private Long thank_from_count;
    private Long thanked_count;
    private ZhihuTopic business;
    private List<ZhihuEmployment> employments;
    private List<ZhihuEducation> educations;
    private List<ZhihuTopic> locations;
}
