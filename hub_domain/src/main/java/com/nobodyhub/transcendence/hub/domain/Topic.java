package com.nobodyhub.transcendence.hub.domain;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Topic {
    /**
     * Unque id of document
     */
    @Id
    private String id;
    /**
     * Topic name
     */
    @Indexed(unique = true)
    private String name;

    private ZhihuTopic zhihuTopic;
}
