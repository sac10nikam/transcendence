package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.nobodyhub.transcendence.zhihu.domain.common.Mergeable;
import lombok.Data;

@Data
public class ZhihuAnswer implements Mergeable {
    private String id;
    private String type;
    private ZhihuQuestion question;
    private ZhihuAuthor author;
    private String url;
    private long created_time;
    private long updated_time;
    private long voteup_count;
    private long comment_count;
    private String content;
    private String excerpt;
}
