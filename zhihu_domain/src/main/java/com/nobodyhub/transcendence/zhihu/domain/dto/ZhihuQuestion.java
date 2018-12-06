package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.nobodyhub.transcendence.zhihu.domain.common.Mergeable;
import lombok.Data;


@Data
public class ZhihuQuestion implements Mergeable {
    private String type;
    private String id;
    private String title;
    private String question_type;
    private String created;
    private String updated_time;
    private String url;
    private Object relationship;
}
