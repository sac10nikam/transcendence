package com.nobodyhub.transcendence.zhihu.member.domain;

import com.nobodyhub.transcendence.mongodb.domain.MongoObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ZhihuMember extends MongoObject<com.nobodyhub.transcendence.zhihu.domain.ZhihuMember> {

    /**
     * @see com.nobodyhub.transcendence.zhihu.domain.ZhihuMember#getId()
     */
    @Indexed
    private String dateId;

    /**
     * @see com.nobodyhub.transcendence.zhihu.domain.ZhihuMember#getUrlToken()
     */
    @Indexed
    private String urlToken;

    @Override
    public void setData(com.nobodyhub.transcendence.zhihu.domain.ZhihuMember data) {
        this.data = data;
        this.dateId = data.getId();
        this.urlToken = data.getUrlToken();
    }
}
