package com.nobodyhub.transcendence.hub.domain;

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
     * Id of including detail dat
     * borrow from the id of corresponding detail
     * - {@link ZhihuAnswer#getId()}
     * - {@link ZhihuComment#getId()}
     */
    @Indexed
    private String dataId;
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
     * id of parent deed
     * if no parent(e.g., root deed), can be null
     */
    private String parentId;
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
