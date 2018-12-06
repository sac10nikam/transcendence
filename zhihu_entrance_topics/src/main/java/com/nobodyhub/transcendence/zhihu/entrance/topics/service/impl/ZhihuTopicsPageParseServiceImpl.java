package com.nobodyhub.transcendence.zhihu.entrance.topics.service.impl;

import com.google.common.collect.Lists;
import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopicCategory;
import com.nobodyhub.transcendence.zhihu.entrance.topics.service.ZhihuTopicApiService;
import com.nobodyhub.transcendence.zhihu.entrance.topics.service.ZhihuTopicsPageParseService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ZhihuTopicsPageParseServiceImpl implements ZhihuTopicsPageParseService {
    private final ZhihuTopicApiService topicApiService;

    @Autowired
    public ZhihuTopicsPageParseServiceImpl(ZhihuTopicApiService topicApiService) {
        this.topicApiService = topicApiService;
    }

    @Override
    public List<ZhihuTopicCategory> getAllCategories() {
        final String topicsUrl = "https://www.zhihu.com/topics";
        log.info("Parse Page from {}...", topicsUrl);
        List<ZhihuTopicCategory> topicCategories = Lists.newArrayList();
        try {
            Document doc = Jsoup.connect(topicsUrl).get();
            Elements categories = doc.select(".zm-topic-cat-item");
            for (Element c : categories) {
                topicCategories.add(
                    new ZhihuTopicCategory(Integer.valueOf(c.attr("data-id")), c.text())
                );
            }
        } catch (IOException e) {
            log.error("Error access URL: [{}].", topicsUrl);
        }
        return topicCategories;
    }
    //TODO: Load Topic page and parse the questions
}
