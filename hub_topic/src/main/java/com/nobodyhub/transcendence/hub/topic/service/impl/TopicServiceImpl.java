package com.nobodyhub.transcendence.hub.topic.service.impl;

import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.client.ZhihuTopicApiClient;
import com.nobodyhub.transcendence.hub.topic.repository.TopicRepository;
import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.nobodyhub.transcendence.hub.domain.Topic.TopicType.ZHIHU_QUESTION;
import static com.nobodyhub.transcendence.hub.domain.Topic.TopicType.ZHIHU_TOPIC;

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
    public Topic find(ZhihuTopic zhihuTopic) {
        Optional<Topic> exist = topicRepository.findFirstByDataIdAndType(zhihuTopic.getId(), ZHIHU_TOPIC);
        if (exist.isPresent()) {
            return exist.get();
        }
        // if non-exist, create new one with zhihu topic
        Topic topic = createFromZhihuTopic(zhihuTopic);
        return this.topicRepository.save(topic);
    }

    @Override
    public Topic find(ZhihuQuestion zhihuQuestion) {
        Optional<Topic> exist = topicRepository.findFirstByDataIdAndType(zhihuQuestion.getId(), ZHIHU_QUESTION);
        if (exist.isPresent()) {
            return exist.get();
        }
        // if non-exist, create new one with zhihu topic
        Topic topic = createFromZhihuQuestion(zhihuQuestion);
        return this.topicRepository.save(topic);
    }


    @Override
    public Topic save(ZhihuTopic zhihuTopic) {
        // find existing topic if any
        Optional<Topic> exist = topicRepository.findFirstByDataIdAndType(zhihuTopic.getId(), ZHIHU_TOPIC);
        if (exist.isPresent()) {
            Topic existTopic = exist.get();
            if (existTopic.getType() == ZHIHU_TOPIC) {
                // if exist, merge with exist
                ZhihuTopic existZhihuTopic = existTopic.getZhihuTopic();
                existTopic.setZhihuTopic(MergeUtils.merge(zhihuTopic, existZhihuTopic));
                return this.topicRepository.save(existTopic);
            }
        }
        // if non-exist, create new one with zhihu topic
        Topic topic = createFromZhihuTopic(zhihuTopic);
        return this.topicRepository.save(topic);
    }

    @Override
    public Topic save(ZhihuQuestion zhihuQuestion) {
        // find existing topic if any
        Optional<Topic> exist = topicRepository.findFirstByDataIdAndType(zhihuQuestion.getId(), ZHIHU_QUESTION);
        if (exist.isPresent()) {
            Topic existTopic = exist.get();
            if (existTopic.getType() == ZHIHU_QUESTION) {
                // if exist, merge with exist
                ZhihuQuestion existZhihuQuestion = existTopic.getZhihuQuestion();
                existTopic.setZhihuQuestion(MergeUtils.merge(zhihuQuestion, existZhihuQuestion));
                return this.topicRepository.save(existTopic);
            }
        }
        // if non-exist, create new one with zhihu topic
        Topic topic = createFromZhihuQuestion(zhihuQuestion);
        return this.topicRepository.save(topic);
    }

    private Topic createFromZhihuTopic(ZhihuTopic zhihuTopic) {
        Topic topic = new Topic();
        topic.setType(ZHIHU_TOPIC);
        topic.setDataId(zhihuTopic.getId());
        topic.setName(zhihuTopic.getName());
        topic.setZhihuTopic(zhihuTopic);
        return topic;
    }

    private Topic createFromZhihuQuestion(ZhihuQuestion zhihuQuestion) {
        Topic topic = new Topic();
        topic.setType(Topic.TopicType.ZHIHU_QUESTION);
        topic.setDataId(zhihuQuestion.getId());
        topic.setName(zhihuQuestion.getTitle());
        topic.setZhihuQuestion(zhihuQuestion);
        return topic;
    }

    @Override
    public void saveZhihuTopicParents(String topicId, Set<ZhihuTopic> parents) {
        Optional<Topic> topic = topicRepository.findFirstByDataIdAndType(topicId, ZHIHU_TOPIC);
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
        parents.forEach(this::save);
    }

    @Override
    public void saveZhihuTopicChildren(String topicId, Set<ZhihuTopic> children) {
        Optional<Topic> topic = topicRepository.findFirstByDataIdAndType(topicId, ZHIHU_TOPIC);
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
        children.forEach(this::save);
    }
}
