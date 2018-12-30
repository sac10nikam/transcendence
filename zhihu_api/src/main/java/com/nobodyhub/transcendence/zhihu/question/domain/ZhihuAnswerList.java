package com.nobodyhub.transcendence.zhihu.question.domain;

import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiAnswer;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiPaging;
import lombok.Data;

import java.util.List;

/**
 * The list of answer replied from Zhihu API
 */
@Data
public class ZhihuAnswerList {
    private List<ZhihuApiAnswer> data;
    private ZhihuApiPaging paging;
}
