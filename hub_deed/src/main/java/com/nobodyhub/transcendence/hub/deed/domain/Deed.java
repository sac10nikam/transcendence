package com.nobodyhub.transcendence.hub.deed.domain;

import com.nobodyhub.transcendence.hub.domain.People;
import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
public class Deed {
    /**
     * Unque id of document
     */
    @Id
    private String id;
    /**
     * Name of Individual
     */
    @Indexed(unique = true)
    private String name;
    /**
     * Contents of deed
     * including html marker if any
     */
    private String content;
    /**
     * A short extract from content
     * contains only plain text
     */
    private String excerpt;
    /**
     * belonging topic
     */
    private Topic topic;
    /**
     * related people
     */
    private People people;
    /**
     * parent deed
     * can be of different type
     * if no parent, or root deed, can be null
     */
    private Deed parent;
    /**
     * created time
     * used to decide the order of the childrent of the same parent
     */
    private Long createdAt;
    /**
     * Type of deed.
     * The corresponding details can not be null
     */
    private DeedType type;
    /**
     * Zhihu Answer
     * not null if {@link #type} == {@link DeedType#ZHIHU_ANSWER}
     */
    private ZhihuAnswer zhihuAnswer;
    /**
     * Zhihu Comment
     * not null if {@link #type} == {@link DeedType#ZHIHU_COMMENT}
     */
    private ZhihuComment zhihuComment;

    public enum DeedType {
        /**
         * Zhihu Answer
         */
        ZHIHU_ANSWER,
        /**
         * Zhihu Comment
         */
        ZHIHU_COMMENT,
        ;
    }
}
