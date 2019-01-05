package com.nobodyhub.transcendence.zhihu.article.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiPaging;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuArticle;
import lombok.Data;

import java.util.List;

@Data
public class ZhihuColumnArticles {
    @JsonProperty("paging")
    private ZhihuApiPaging paging;
    @JsonProperty("data")
    private List<ZhihuArticle> data;
}
