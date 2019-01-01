package com.nobodyhub.transcendence.hub.domain;

import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class People {
    /**
     * Unque id of document
     */
    @Id
    private String id;
    /**
     * Id of including detail data
     * borrow from the id of corresponding detail
     * - {@link ZhihuMember#getUrlToken()}
     */
    @Indexed
    private String dataId;
    /**
     * Name of Individual
     */
    @Indexed(unique = true)
    private String name;

    /**
     * Type of people
     * the corresponding detail should not be null
     */
    private PeopleType type;

    private ZhihuMember zhihuMember;

    public enum PeopleType {
        /**
         * Zhihu Member
         */
        ZHIHU_MEMBER,
        ;
    }
}
