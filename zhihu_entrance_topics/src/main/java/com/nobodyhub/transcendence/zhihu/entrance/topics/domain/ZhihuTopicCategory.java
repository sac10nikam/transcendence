package com.nobodyhub.transcendence.zhihu.entrance.topics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * parsed from .zm-topic-cat-item in https://www.zhihu.com/topics
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class ZhihuTopicCategory {
    private Integer dataId;
    private String name;
}
