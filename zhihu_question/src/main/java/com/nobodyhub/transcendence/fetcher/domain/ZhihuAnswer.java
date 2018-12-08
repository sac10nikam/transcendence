package com.nobodyhub.transcendence.fetcher.domain;

import lombok.Data;

@Data
public class ZhihuAnswer {
    private String id;
    private String type;
    private ZhihuQuestion question;
    private ZhihuMember author;
    private String url;
    private long created_time;
    private long updated_time;
    private long voteup_count;
    private long comment_count;
    private String content;
    private String excerpt;
}
