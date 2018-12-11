package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.zhihu.domain.merge.Mergeable;
import lombok.Data;

@Data
public class ZhihuEducation implements Mergeable {
    @JsonProperty("major")
    private ZhihuTopic major;
    @JsonProperty("school")
    private ZhihuTopic school;
    @JsonProperty("diploma")
    private int diploma;
}
