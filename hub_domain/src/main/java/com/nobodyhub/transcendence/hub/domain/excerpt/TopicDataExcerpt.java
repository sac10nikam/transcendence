package com.nobodyhub.transcendence.hub.domain.excerpt;

import com.nobodyhub.transcendence.common.domain.DataExcerpt;
import com.nobodyhub.transcendence.hub.domain.Topic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * An excerpt of topic data
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TopicDataExcerpt extends DataExcerpt<Topic.TopicType> {

    private TopicDataExcerpt(Topic.TopicType type, String id) {
        super(type, id);
    }

    public static TopicDataExcerpt of(Topic.TopicType type, String id) {
        return new TopicDataExcerpt(type, id);
    }
}
