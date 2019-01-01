package com.nobodyhub.transcendence.hub.domain;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
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
     * Id of including detail data
     * not-null
     * borrow from the id of corresponding detail
     * - {@link ZhihuTopic#getId()}
     * - {@link ZhihuQuestion#getId()}
     */
    @Indexed
    private String dataId;
    /**
     * Topic name
     * not-null
     */
    @Indexed
    private String name;
    /**
     * Topic type
     * the corresponding detail should not be null
     */
    private TopicType type;

    private ZhihuTopic zhihuTopic;

    private ZhihuQuestion zhihuQuestion;

    public enum TopicType {
        /**
         * Zhihu Answer
         */
        ZHIHU_TOPIC,
        /**
         * Zhihu Comment
         */
        ZHIHU_QUESTION,
        ;
    }
}
