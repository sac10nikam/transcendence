package com.nobodyhub.transcendence.zhihu.topic.domain;

import com.google.common.collect.Sets;
import com.nobodyhub.transcendence.zhihu.domain.common.Mergeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * Data returned by http://www.zhihu.com/api/v4/topics/{id}
 */
@Document
@Data
@EqualsAndHashCode(of = "id")
public class ZhihuTopic implements Mergeable {
    @Id
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
    /**
     * 父话题
     */
    @DBRef
    private Set<ZhihuTopic> parentTopics;
    /**
     * 子话题
     */
    @DBRef
    private Set<ZhihuTopic> childTopics;

    public void addCategory(ZhihuTopicCategory category) {
        if (this.categories == null) {
            this.categories = Sets.newHashSet();
        }
        this.categories.add(category);
    }
}
