package com.nobodyhub.transcendence.hub.domain;

import com.nobodyhub.transcendence.hub.domain.abstr.HubObject;
import com.nobodyhub.transcendence.hub.domain.excerpt.PeopleDataExcerpt;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The individual that performs independently on expressing their opinions.
 *
 * @see Topic
 * @see Deed
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class People extends HubObject<PeopleDataExcerpt> {
    /**
     * Unque id of document
     */
    @Id
    private String id;
    /**
     * Name of Individual
     */
    @Indexed
    private String name;

    /**
     * @see PeopleType#ZHIHU_MEMBER
     */
    private ZhihuMember zhihuMember;

    /**
     * Type of People data
     */
    public enum PeopleType {
        /**
         * Zhihu Member
         *
         * @see ZhihuMember
         */
        ZHIHU_MEMBER,
        ;
    }
}
