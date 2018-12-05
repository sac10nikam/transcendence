package com.nobodyhub.transcendence.fetcher.domain;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * The list of answer replied from Zhihu API
 */
@Data
public class ZhihuAnswerList {
    private List<ZhihuAnswer> data;
    private ZhihuPaging paging;
}
