package com.nobodyhub.transcendence.zhihu.answer.domain;

import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiPaging;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import lombok.Data;

import java.util.List;

/**
 * The list of answer replied from Zhihu API
 */
@Data
public class ZhihuAnswerList {
    private List<ZhihuAnswer> data;
    private ZhihuApiPaging paging;
}
