package com.nobodyhub.transcendence.hub.domain;

import com.nobodyhub.transcendence.hub.domain.abstr.HubObject;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuColumn;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

/**
 * A subject or matter towards which people can express their opinions.
 *
 * @see People
 * @see Deed
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class Topic extends HubObject {
    /**
     * Unque id of document
     */
    @Id
    private String id;

    /**
     * Topic name or a simple description of the topic
     */
    @Indexed
    @NonNull
    private String name;
    /**
     * Supported Topic type
     * the corresponding detail should not be null
     *
     * @see TopicType
     */
    @Indexed
    @NonNull
    private TopicType type;

    /**
     * Id of including detail data
     * <p>
     * borrow from the logical id of corresponding detail
     * - {@link ZhihuTopic#getId()}
     * - {@link ZhihuQuestion#getId()}
     * - {@link ZhihuColumn#getId()}
     */
    @Indexed
    @NonNull
    private String dataId;

    private ZhihuTopic zhihuTopic;

    private ZhihuQuestion zhihuQuestion;

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
