package com.nobodyhub.transcendence.zhihu.topic.domain;

import lombok.Data;

import java.util.List;

/**
 * Returned datastructure from https://www.zhihu.com/node/TopicsPlazzaListV2
 */
@Data
public class ZhihuTopicPlazzaListV2 {
    private int r;
    /**
     * contains a list of html elements
     */
    private List<String> msg;
}
