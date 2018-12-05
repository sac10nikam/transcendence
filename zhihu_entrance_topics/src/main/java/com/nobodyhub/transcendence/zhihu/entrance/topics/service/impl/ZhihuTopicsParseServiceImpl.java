package com.nobodyhub.transcendence.zhihu.entrance.topics.service.impl;

import com.google.common.collect.Lists;
import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopicCategory;
import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopicPlazzaListV2;
import com.nobodyhub.transcendence.zhihu.entrance.topics.service.ZhihuTopicsParseService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ZhihuTopicsParseServiceImpl implements ZhihuTopicsParseService {
    private final RestTemplate restTemplate;

    @Autowired
    public ZhihuTopicsParseServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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

    @Override
    public List<ZhihuTopic> getAllTopicsOfCategory(ZhihuTopicCategory category) {
        List<String> topicIds = getTopicIdList(category.getDataId(), 0);
        List<ZhihuTopic> topics = Lists.newArrayList();
        for (String id : topicIds) {
            log.info("Access URL: {}",
                this.restTemplate.getUriTemplateHandler().expand("/topics/{id}", id));
            //TODO: topic can be null
            ZhihuTopic topic = this.restTemplate.getForObject("/topics/{id}", ZhihuTopic.class, id);
            topic.addCategory(category);
            topics.add(topic);
        }
        return topics;
    }
    //TODO: Load Topic page and parse the questions

    private List<String> getTopicIdList(int topicId, int offset) {
        List<String> topicUrls = Lists.newArrayList();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("method", "next");
        map.add("params", String.format("{\"topic_id\":%d,\"offset\":%d,\"hash_id\":\"\"}",
            topicId, offset));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<ZhihuTopicPlazzaListV2> response = this.restTemplate.postForEntity("https://www.zhihu.com/node/TopicsPlazzaListV2",
            request, ZhihuTopicPlazzaListV2.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            List<String> htmls = response.getBody().getMsg();
            for (String html : htmls) {
                Elements links = Jsoup.parse(html).select(".blk a");
                for (Element link : links) {
                    topicUrls.add(link.attr("href").replace("/topic/", ""));
                }
            }
        }
        if (!topicUrls.isEmpty()) {
            topicUrls.addAll(getTopicIdList(topicId, offset + topicUrls.size()));
        }
        return topicUrls;
    }
}
