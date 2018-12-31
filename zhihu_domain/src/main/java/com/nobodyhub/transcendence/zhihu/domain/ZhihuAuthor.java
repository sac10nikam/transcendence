package com.nobodyhub.transcendence.zhihu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * A wrapper of {@link ZhihuMember}
 */
@Data
public class ZhihuAuthor {
    @JsonProperty("member")
    private ZhihuMember member;
    @JsonProperty("role")
    private String role;
}
