package com.nobodyhub.transcendence.zhihu.question.domain;

import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuAnswer;
import lombok.Data;

import java.util.List;

/**
 * The list of answer replied from Zhihu API
 */
@Data
public class ZhihuAnswerList {
    private List<ZhihuAnswer> data;
    private ZhihuPaging paging;
}
