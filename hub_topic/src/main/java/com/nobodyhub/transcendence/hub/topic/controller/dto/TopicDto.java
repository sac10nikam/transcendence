package com.nobodyhub.transcendence.hub.topic.controller.dto;

import com.nobodyhub.transcendence.common.domain.TopicData;
import com.nobodyhub.transcendence.hub.domain.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response object from Topic
 */
@AllArgsConstructor
@Data
public class TopicDto {
    /**
     * Document Id
     *
     * @see Topic#getId()
     */
    private String id;
    /**
     * The type of {@link #data topic content}
     */
    private Topic.TopicType type;
    /**
     * The topic contents for {@link #type type}
     */
    private TopicData data;
}
