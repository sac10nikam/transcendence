package com.nobodyhub.transcendence.hub.topic.controller.dto;

import com.google.common.collect.Lists;
import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.domain.excerpt.TopicDataExcerpt;

import java.util.List;
import java.util.stream.Stream;

/**
 * Convert {@link Topic} from dto object
 */
public final class TopicDtoConverter {

    private TopicDtoConverter() {
    }

    public static List<TopicDto> from(Topic topic) {
        List<TopicDto> dtos = Lists.newArrayList();
        for (TopicDataExcerpt excerpt : topic.getExcerpts()) {
            switch (excerpt.getType()) {
                case ZHIHU_TOPIC: {
                    dtos.add(new TopicDto(topic.getId(), excerpt.getType(), topic.getZhihuTopic()));
                    break;
                }
                case ZHIHU_QUESTION: {
                    dtos.add(new TopicDto(topic.getId(), excerpt.getType(), topic.getZhihuQuestion()));
                    break;
                }
                case ZHIHU_COLUMN: {
                    dtos.add(new TopicDto(topic.getId(), excerpt.getType(), topic.getZhihuColumn()));
                    break;
                }
            }
        }
        return dtos;
    }

    public static List<TopicDto> from(Topic... topics) {
        final List<TopicDto> list = Lists.newArrayList();
        Stream.of(topics).forEach(topic -> list.addAll(TopicDtoConverter.from(topic)));
        return list;
    }

    public static List<TopicDto> from(List<Topic> topics) {
        final List<TopicDto> list = Lists.newArrayList();
        topics.forEach(topic -> list.addAll(TopicDtoConverter.from(topic)));
        return list;
    }
}
