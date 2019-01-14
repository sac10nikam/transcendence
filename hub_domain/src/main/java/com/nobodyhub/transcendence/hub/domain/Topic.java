package com.nobodyhub.transcendence.hub.domain;

import com.nobodyhub.transcendence.hub.domain.abstr.HubObject;
import com.nobodyhub.transcendence.hub.domain.abstr.TopicDataExcerpt;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuColumn;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A subject or matter towards which people can express their opinions.
 *
 * @see People
 * @see Deed
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class Topic extends HubObject<TopicDataExcerpt> {
    /**
     * Unque id of document
     */
    @Id
    private String id;
    /**
     * Topic name
     * not-null
     */
    @Indexed
    private String name;

    /**
     * @see TopicType#ZHIHU_TOPIC
     */
    private ZhihuTopic zhihuTopic;

    /**
     * @see TopicType#ZHIHU_QUESTION
     */
    private ZhihuQuestion zhihuQuestion;

    /**
     * @see TopicType#ZHIHU_COLUMN
     */
    private ZhihuColumn zhihuColumn;

    public enum TopicType {
        /**
         * Zhihu Topic,
         *
         * @see ZhihuTopic
         */
        ZHIHU_TOPIC,
        /**
         * Zhihu Question
         *
         * @see ZhihuQuestion
         */
        ZHIHU_QUESTION,
        /**
         * Zhihu Column
         *
         * @see ZhihuColumn
         */
        ZHIHU_COLUMN,
        ;
    }
}
