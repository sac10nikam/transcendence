package com.nobodyhub.transcendence.hub.topic.service.impl;

import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.client.ZhihuTopicApiClient;
import com.nobodyhub.transcendence.hub.topic.repository.TopicRepository;
import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        Optional<Topic> exist = topicRepository.findFirstByZhihuTopic_Id(zhihuTopic.getId());
        if (exist.isPresent()) {
            Topic existTopic = exist.get();
            // if exist, update zhihu topic
            ZhihuTopic existZhihuTopic = existTopic.getZhihuTopic();
            if (existZhihuTopic != null) {
                existTopic.setZhihuTopic(MergeUtils.merge(zhihuTopic, existZhihuTopic));
                this.topicRepository.save(existTopic);
            }
        } else {
            // if non-exist, create new one with zhihu topic
            Topic topic = new Topic();
            topic.setName(zhihuTopic.getName());
            topic.setZhihuTopic(zhihuTopic);
        }
    }

    @Override
    public void saveZhihuTopicParents(String topicId, Set<ZhihuTopic> parents) {
        Optional<Topic> topic = this.topicRepository.findFirstByZhihuTopic_Id(topicId);
        if (topic.isPresent()) {
            // if exist, update zhihu topic
            ZhihuTopic exist = topic.get().getZhihuTopic();
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
    public void saveZhihuTopicChildren(String topicId, Set<ZhihuTopic> children) {
        Optional<Topic> topic = this.topicRepository.findFirstByZhihuTopic_Id(topicId);
        if (topic.isPresent()) {
            // if exist, update zhihu topic
            ZhihuTopic exist = topic.get().getZhihuTopic();
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
