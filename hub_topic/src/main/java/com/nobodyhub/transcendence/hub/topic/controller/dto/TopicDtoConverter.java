package com.nobodyhub.transcendence.hub.topic.controller.dto;

import com.nobodyhub.transcendence.common.domain.TopicData;
import com.nobodyhub.transcendence.hub.domain.Topic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Convert {@link Topic} from dto object
 */
public final class TopicDtoConverter {

    private TopicDtoConverter() {
    }

    public static TopicDto from(Topic topic) {
        TopicData data = null;
        switch (topic.getType()) {
            case ZHIHU_TOPIC: {
                data = topic.getZhihuTopic();
                break;
            }
            case ZHIHU_QUESTION: {
                data = topic.getZhihuQuestion();
                break;
            }
            case ZHIHU_COLUMN: {
                data = topic.getZhihuColumn();
                break;
            }
        }
        return new TopicDto(topic.getId(), topic.getType(), data);
    }

    public static List<TopicDto> from(Topic... topics) {
        return Stream.of(topics)
            .map(TopicDtoConverter::from)
            .collect(Collectors.toList());
    }

    public static List<TopicDto> from(List<Topic> topics) {
        return topics.stream()
            .map(TopicDtoConverter::from)
            .collect(Collectors.toList());
    }
}
