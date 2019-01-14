package com.nobodyhub.transcendence.hub.domain;

import com.nobodyhub.transcendence.hub.domain.abstr.HubObject;
import com.nobodyhub.transcendence.hub.domain.excerpt.DeedDataExcerpt;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuArticle;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The verbal actions of each individual showing their attitudes towards topics.
 *
 * @see Topic
 * @see People
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class Deed extends HubObject<DeedDataExcerpt> {
    /**
     * Unque id of document
     */
    @Id
    private String id;
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
     * used to decide the order of the children of the same parent
     */
    private Long createdAt;
    /**
     * Zhihu Answer
     *
     * @see DeedType#ZHIHU_ANSWER
     */
    private ZhihuAnswer zhihuAnswer;
    /**
     * Zhihu Comment
     *
     * @see DeedType#ZHIHU_COMMENT
     */
    private ZhihuComment zhihuComment;
    /**
     * Zhihu Article
     *
     * @see DeedType#ZHIHU_ARTICLE
     */
    private ZhihuArticle zhihuArticle;

    public enum DeedType {
        /**
         * Zhihu Answer
         *
         * @see ZhihuAnswer
         */
        ZHIHU_ANSWER,
        /**
         * Zhihu Comment
         *
         * @see ZhihuComment
         */
        ZHIHU_COMMENT,
        /**
         * Zhihu Article
         *
         * @see ZhihuArticle
         */
        ZHIHU_ARTICLE,
        ;
    }
}
