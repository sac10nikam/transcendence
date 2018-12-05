package com.nobodyhub.transcendence.fetcher.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
public class ZhihuAuthor {
    private String id;
    private String url_token;
    private String name;
    private String avatar_url;
    private String avatar_url_template;
    private Boolean is_org;
    private String type;
    private String url;
    private String user_type;
    private String headline;
    private int gender;
    private Boolean is_advertiser;
    private int follower_count;
    private int answer_count;
    private int articles_count;
    private List<ZhihuEmployment> employments;
}
