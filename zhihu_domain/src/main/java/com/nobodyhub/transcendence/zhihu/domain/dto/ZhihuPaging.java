package com.nobodyhub.transcendence.zhihu.domain.dto;

import com.nobodyhub.transcendence.zhihu.domain.merge.Mergeable;
import lombok.Data;

@Data
public class ZhihuPaging implements Mergeable {
    private Boolean is_end;
    private Boolean is_start;
    private String next;
    private String previous;
    private Long total;
}
