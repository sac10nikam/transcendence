package com.nobodyhub.transcendence.fetcher.domain;

import lombok.Data;

/**
 * A generic datastructure to represent topic, like job, company. and etc.
 */
@Data
public class ZhihuTopic {
    private String introduction;
    private String avatar_url;
    private String name;
    private String url;
    private String type;
    private String excerpt;
    private String id;
}
