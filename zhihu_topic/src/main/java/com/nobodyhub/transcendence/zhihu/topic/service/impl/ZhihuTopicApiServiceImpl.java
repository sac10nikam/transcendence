package com.nobodyhub.transcendence.zhihu.topic.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nobodyhub.transcendence.zhihu.domain.MergeUtils;
import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopicCategory;
import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopicPlazzaListV2;
import com.nobodyhub.transcendence.zhihu.topic.domain.paging.ZhihuTopicList;
import com.nobodyhub.transcendence.zhihu.topic.repository.ZhihuTopicRepository;
import com.nobodyhub.transcendence.zhihu.topic.service.ZhihuTopicApiService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ZhihuTopicApiServiceImpl implements ZhihuTopicApiService {
    private final RestTemplate restTemplate;
    private final ZhihuTopicRepository topicRepository;

    @Autowired
    public ZhihuTopicApiServiceImpl(RestTemplate restTemplate, ZhihuTopicRepository topicRepository) {
        this.restTemplate = restTemplate;
        this.topicRepository = topicRepository;
    }

    @Override
    public Optional<ZhihuTopic> getTopic(String topicId) {
        final String topicUrl = "/api/v4/topics/{id}";
        final String topicParentUrl = "/api/v3/topics/{id}/parent";
        final String topicChildUrl = "/api/v3/topics/{id}/children";
        try {
            Set<ZhihuTopic> parents = getByPaging(topicParentUrl, topicId);
            Set<ZhihuTopic> children = getByPaging(topicChildUrl, topicId);

            log.debug("Accessing : [{}]",
                this.restTemplate.getUriTemplateHandler().expand(topicUrl, topicId));
            Optional<ZhihuTopic> topic = Optional.ofNullable(this.restTemplate.getForObject(topicUrl, ZhihuTopic.class, topicId));
            if (topic.isPresent()) {
                topic.get().setParentTopics(parents);
                topic.get().setChildTopics(children);
            }
            return topic;
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
            Optional<ZhihuTopic> newTopic = getTopic(id);
            if (newTopic.isPresent()) {
                newTopic.get().addCategory(category);
                Optional<ZhihuTopic> oldTopic = this.topicRepository.findById(id);
                if (oldTopic.isPresent()) {
                    //if old exist, update exist with the merged topic
                    topics.add(this.topicRepository.save(MergeUtils.merge(newTopic.get(), oldTopic.get())));
                } else {
                    //if non-exist, save the new topic
                    topics.add(this.topicRepository.save(newTopic.get()));
                }
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

    /**
     * handle the paging data for parent/children topics
     *
     * @param url
     * @param topicId
     * @return
     */
    private Set<ZhihuTopic> getByPaging(String url, String topicId) {
        Set<ZhihuTopic> topics = Sets.newHashSet();
        ZhihuTopicList topicList = null;
        try {
            log.debug("Accessing : [{}]",
                this.restTemplate.getUriTemplateHandler().expand(url, topicId));
            topicList = this.restTemplate.getForObject(url, ZhihuTopicList.class, topicId);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error when access URL: [{}]",
                this.restTemplate.getUriTemplateHandler().expand(url, topicId));
            log.error(e.getMessage());
        }
        if (topicList != null
            && !topicList.getData().isEmpty()
            && !topicList.getPaging().getIs_end()) {
            topics.addAll(getByPaging(topicList.getPaging().getNext(), topicId));
            topics.addAll(topicList.getData());
        }
        return topics;
    }
}
