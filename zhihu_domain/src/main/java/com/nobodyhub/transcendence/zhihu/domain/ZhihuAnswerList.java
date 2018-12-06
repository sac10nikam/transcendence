package com.nobodyhub.transcendence.zhihu.domain;

import lombok.Data;

import java.util.List;

/**
 * The list of answer replied from Zhihu API
 */
@Data
public class ZhihuAnswerList implements Mergeable {
    private List<ZhihuAnswer> data;
    private ZhihuPaging paging;
}
