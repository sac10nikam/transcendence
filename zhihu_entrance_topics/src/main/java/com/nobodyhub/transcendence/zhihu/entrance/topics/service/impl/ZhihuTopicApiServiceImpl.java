package com.nobodyhub.transcendence.zhihu.entrance.topics.service.impl;

import com.google.common.collect.Lists;
import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopicCategory;
import com.nobodyhub.transcendence.zhihu.entrance.topics.domain.ZhihuTopicPlazzaListV2;
import com.nobodyhub.transcendence.zhihu.entrance.topics.service.ZhihuTopicApiService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ZhihuTopicApiServiceImpl implements ZhihuTopicApiService {
    private final RestTemplate restTemplate;

    public ZhihuTopicApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<ZhihuTopic> getTopic(String topicId) {
        final String topicUrl = "/topics/{id}";
        try {
            log.info("Accessing : [{}]",
                this.restTemplate.getUriTemplateHandler().expand(topicUrl, topicId));
            return Optional.ofNullable(this.restTemplate.getForObject(topicUrl, ZhihuTopic.class, topicId));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error when access URL: [{}]",
                this.restTemplate.getUriTemplateHandler().expand(topicUrl, topicId));
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<ZhihuTopic> getTopicsByCategory(ZhihuTopicCategory category) {
        List<String> topicIds = getTopicIdList(category.getDataId(), 0);
        List<ZhihuTopic> topics = Lists.newArrayList();
        for (String id : topicIds) {
            Optional<ZhihuTopic> topic = getTopic(id);
            if (topic.isPresent()) {
                topic.get().addCategory(category);
                topics.add(topic.get());
            }
        }
        return topics;
    }

    /**
     * get a list of topics of given category, starting form offset
     *
     * @param categoryId
     * @param offset
     * @return
     */
    private List<String> getTopicIdList(int categoryId, int offset) {
        final String topicPlazzaUrl = "https://www.zhihu.com/node/TopicsPlazzaListV2";
        List<String> topicUrls = Lists.newArrayList();
        // header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // form data
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("method", "next");
        map.add("params", String.format("{\"topic_id\":%d,\"offset\":%d,\"hash_id\":\"\"}",
            categoryId, offset));
        // request entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        try {
            log.debug("Access URL: [{}]", topicPlazzaUrl);
            ResponseEntity<ZhihuTopicPlazzaListV2> response = this.restTemplate.postForEntity(
                topicPlazzaUrl, request, ZhihuTopicPlazzaListV2.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<String> htmls = response.getBody().getMsg();
                for (String html : htmls) {
                    Elements links = Jsoup.parse(html).select(".blk a:not(.zg-follow)");
                    for (Element link : links) {
                        topicUrls.add(link.attr("href").replace("/topic/", ""));
                    }
                }
            }
        } catch (HttpClientErrorException e) {
            log.error("Error access URL: [{}]", topicPlazzaUrl);
            log.error(e.getMessage());
        }
        // try to fetch for more topics in this category
        if (!topicUrls.isEmpty()) {
            topicUrls.addAll(getTopicIdList(categoryId, offset + topicUrls.size()));
        }
        return topicUrls;
    }
}
