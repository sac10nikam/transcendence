package com.nobodyhub.transcendence.hub.topic.service.impl;

import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.hub.topic.client.ZhihuTopicApiClient;
import com.nobodyhub.transcendence.hub.topic.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.repository.TopicRepository;
import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final ZhihuTopicApiClient zhihuTopicApiClient;

    public TopicServiceImpl(TopicRepository topicRepository,
                            ZhihuTopicApiClient zhihuTopicApiClient) {
        this.topicRepository = topicRepository;
        this.zhihuTopicApiClient = zhihuTopicApiClient;
    }

    @Override
    public void saveZhihuTopic(ZhihuTopic zhihuTopic) {
        // find existing topic if any
        List<Topic> topics = topicRepository.findByZhihuTopic_Id(zhihuTopic.getId());
        if (topics != null && !topics.isEmpty()) {
            // if exist, update zhihu topic
            Topic topic = topics.get(0);
            ZhihuTopic exist = topic.getZhihuTopic();
            if (exist != null) {
                topic.setZhihuTopic(MergeUtils.merge(zhihuTopic, exist));
                this.topicRepository.save(topic);
            }
        } else {
            // if non-exist, create new one with zhihu topic
            Topic topic = new Topic();
            topic.setName(zhihuTopic.getName());
            topic.setZhihuTopic(zhihuTopic);
        }
    }

    @Override
    public void saveZhihuTopicParent(String topicId, Set<ZhihuTopic> parents) {
        List<Topic> topics = this.topicRepository.findByZhihuTopic_Id(topicId);
        if (topics != null && !topics.isEmpty()) {
            // if exist, update zhihu topic
            Topic topic = topics.get(0);
            ZhihuTopic exist = topic.getZhihuTopic();
            if (exist != null) {
                exist.setParents(parents);
            }
        } else {
            zhihuTopicApiClient.getTopicById(topicId);
        }
        // saven parents
        parents.forEach(this::saveZhihuTopic);
    }

    @Override
    public void saveZhihuTopicChild(String topicId, Set<ZhihuTopic> children) {
        List<Topic> topics = this.topicRepository.findByZhihuTopic_Id(topicId);
        if (topics != null && !topics.isEmpty()) {
            // if exist, update zhihu topic
            Topic topic = topics.get(0);
            ZhihuTopic exist = topic.getZhihuTopic();
            if (exist != null) {
                exist.setChildren(children);
            }
        } else {
            zhihuTopicApiClient.getTopicById(topicId);
        }
        // saven parents
        children.forEach(this::saveZhihuTopic);
    }
}
