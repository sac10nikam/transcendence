package com.nobodyhub.transcendence.zhihu.member.domain;

import com.nobodyhub.transcendence.mongodb.domain.MongoObject;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiMember;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ZhihuMember extends MongoObject<ZhihuApiMember> {

    /**
     * @see ZhihuApiMember#getId()
     */
    @Indexed
    private String dateId;

    /**
     * @see ZhihuApiMember#getUrlToken()
     */
    @Indexed
    private String urlToken;

    @Override
    public void setData(ZhihuApiMember data) {
        this.data = data;
        this.dateId = data.getId();
        this.urlToken = data.getUrlToken();
    }
}
