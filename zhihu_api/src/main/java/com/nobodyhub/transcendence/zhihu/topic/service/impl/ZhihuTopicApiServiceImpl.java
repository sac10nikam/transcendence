package com.nobodyhub.transcendence.zhihu.topic.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.zhihu.common.converter.ZhihuUrlConverter;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiAnswer;
import com.nobodyhub.transcendence.zhihu.member.service.ZhihuMemberService;
import com.nobodyhub.transcendence.zhihu.question.service.ZhihuQuestionService;
import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopicCategory;
import com.nobodyhub.transcendence.zhihu.topic.domain.feed.ZhihuTopicFeed;
import com.nobodyhub.transcendence.zhihu.topic.domain.feed.ZhihuTopicFeedList;
import com.nobodyhub.transcendence.zhihu.topic.domain.paging.ZhihuTopicList;
import com.nobodyhub.transcendence.zhihu.topic.domain.plazza.ZhihuTopicPlazzaList;
import com.nobodyhub.transcendence.zhihu.topic.message.ZhihuTopicMessager;
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

/**
 * @deprecated use {@link ZhihuTopicMessager} instead
 */
@Slf4j
@Service
@Deprecated
public class ZhihuTopicApiServiceImpl implements ZhihuTopicApiService {
    private final RestTemplate restTemplate = null;
    private final ZhihuTopicRepository topicRepository;
    private final ZhihuMemberService zhihuMemberService;
    private final ZhihuQuestionService zhihuQuestionService;
    private final ZhihuUrlConverter urlConverter;

    @Autowired
    public ZhihuTopicApiServiceImpl(ZhihuTopicRepository topicRepository,
                                    ZhihuMemberService zhihuMemberService,
                                    ZhihuQuestionService zhihuQuestionService,
                                    ZhihuUrlConverter urlConverter) {
        this.topicRepository = topicRepository;
        this.zhihuMemberService = zhihuMemberService;
        this.zhihuQuestionService = zhihuQuestionService;
        this.urlConverter = urlConverter;
    }

    @Override
    public Optional<ZhihuTopic> getTopic(String topicId) {
        final String topicUrl = "/api/v4/topics/{id}";
        final String topicParentUrl = "/api/v4/topics/{id}/parent";
        final String topicChildUrl = "/api/v4/topics/{id}/children";
        try {
            Set<ZhihuTopic> parents = getTopicsByPaging(topicParentUrl, topicId);
            Set<ZhihuTopic> children = getTopicsByPaging(topicChildUrl, topicId);

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

    @Override
    public List<ZhihuApiAnswer> getAnswerByTopic(String topicId) {
        List<ZhihuApiAnswer> answers = Lists.newArrayList(
            getAnswersByPaging("/api/v4/topics/{topicId}/feeds/essence?include=data[?(target.type=topic_sticky_module)].target.data[?(target.type=answer)].target.content,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp;data[?(target.type=topic_sticky_module)].target.data[?(target.type=answer)].target.is_normal,comment_count,voteup_count,content,relevant_info,excerpt.author.badge[?(type=best_answerer)].topics;data[?(target.type=topic_sticky_module)].target.data[?(target.type=article)].target.content,voteup_count,comment_count,voting,author.badge[?(type=best_answerer)].topics;data[?(target.type=topic_sticky_module)].target.data[?(target.type=people)].target.answer_count,articles_count,gender,follower_count,is_followed,is_following,badge[?(type=best_answerer)].topics;data[?(target.type=answer)].target.annotation_detail,content,hermes_label,is_labeled,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp;data[?(target.type=answer)].target.author.badge[?(type=best_answerer)].topics;data[?(target.type=article)].target.annotation_detail,content,hermes_label,is_labeled,author.badge[?(type=best_answerer)].topics;data[?(target.type=question)].target.annotation_detail,comment_count;&limit=10",
                topicId)
        );
        answers.forEach(answer -> {
            //TODO: save answer
            zhihuQuestionService.save(answer.getQuestion());
            zhihuMemberService.save(answer.getAuthor());
        });
        return answers;
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
            ResponseEntity<ZhihuTopicPlazzaList> response = this.restTemplate.postForEntity(
                topicPlazzaUrl, request, ZhihuTopicPlazzaList.class);
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
    private Set<ZhihuTopic> getTopicsByPaging(String url, String topicId) {
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
            && !topicList.getPaging().getIsEnd()) {
            topics.addAll(getTopicsByPaging(topicList.getPaging().getNext(), topicId));
            topics.addAll(topicList.getData());
        }
        return topics;
    }

    /**
     * handle the paging data for the answers of given topic
     *
     * @param url     api url
     * @param topicId the belonging topic
     * @return
     */
    private Set<ZhihuApiAnswer> getAnswersByPaging(String url, String topicId) {
        Set<ZhihuApiAnswer> answers = Sets.newHashSet();
        ZhihuTopicFeedList feedList = null;
        try {
            log.info("Accessing : [{}]",
                this.restTemplate.getUriTemplateHandler().expand(url, topicId));
            feedList = this.restTemplate.getForObject(url, ZhihuTopicFeedList.class, topicId);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error when access URL: [{}]",
                this.restTemplate.getUriTemplateHandler().expand(url, topicId));
            log.error(e.getMessage());
        }
        if (feedList != null
            && !feedList.getData().isEmpty()
            && !feedList.getPaging().getIsEnd()) {
            answers.addAll(getAnswersByPaging(urlConverter.convert(feedList.getPaging().getNext()), topicId));
            for (ZhihuTopicFeed feed : feedList.getData()) {
                answers.add(feed.getTarget());
            }
        }
        return answers;
    }
}
