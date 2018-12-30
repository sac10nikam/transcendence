package com.nobodyhub.transcendence.zhihu.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.zhihu.member.domain.ZhihuMember;
import lombok.Data;

@Data
public class ZhihuApiAuthor {
    @JsonProperty("member")
    private ZhihuMember member;
    @JsonProperty("role")
    private String role;
}
