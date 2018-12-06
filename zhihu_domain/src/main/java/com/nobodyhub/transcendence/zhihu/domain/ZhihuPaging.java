package com.nobodyhub.transcendence.zhihu.domain;

import lombok.Data;

@Data
public class ZhihuPaging {
    private Boolean is_end;
    private Boolean is_start;
    private String next;
    private String previous;
    private Long total;
}
