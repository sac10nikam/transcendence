package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.nobodyhub.transcendence.zhihu.domain.common.Mergeable;
import lombok.Data;

@Data
public class ZhihuEducation implements Mergeable {
    private ZhihuTopic major;
    private ZhihuTopic school;
    private int diploma;
}
