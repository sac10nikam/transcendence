package com.nobodyhub.transcendence.zhihu.topic.domain;

import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

/**
 * Data returned by http://www.zhihu.com/api/v4/topics/{id}
 */
@Data
public class ZhihuTopic {
    private String id;
    private String name;
    private String url;
    private String introduction;
    private String avatar_url;
    private Boolean is_vote;
    private String excerpt;
    private Long unanswered_count;
    private Long best_answerers_count;
    private Long questions_count;
    private Long father_count;
    private Long followers_count;
    private Long best_answers_count;
    private String type;
    private Set<ZhihuTopicCategory> categories;

    public void addCategory(ZhihuTopicCategory category) {
        if (this.categories == null) {
            this.categories = Sets.newHashSet();
        }
        this.categories.add(category);
    }
}
