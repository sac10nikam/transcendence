package com.nobodyhub.transcendence.hub.topic.service.impl;

import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.topic.client.ZhihuColumnApiClient;
import com.nobodyhub.transcendence.hub.topic.client.ZhihuQuestionApiClient;
import com.nobodyhub.transcendence.hub.topic.client.ZhihuTopicApiClient;
import com.nobodyhub.transcendence.hub.topic.repository.TopicFuzzyRepository;
import com.nobodyhub.transcendence.hub.topic.repository.TopicRepository;
import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuColumn;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.nobodyhub.transcendence.hub.domain.Topic.TopicType.*;

@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicFuzzyRepository topicFuzzyRepository;

    private final ZhihuTopicApiClient zhihuTopicApiClient;
    private final ZhihuQuestionApiClient zhihuQuestionApiClient;
    private final ZhihuColumnApiClient zhihuColumnApiClient;

    public TopicServiceImpl(TopicRepository topicRepository,
                            TopicFuzzyRepository topicFuzzyRepository,
                            ZhihuTopicApiClient zhihuTopicApiClient,
                            ZhihuQuestionApiClient zhihuQuestionApiClient,
                            ZhihuColumnApiClient zhihuColumnApiClient) {
        this.topicRepository = topicRepository;
        this.topicFuzzyRepository = topicFuzzyRepository;
        this.zhihuTopicApiClient = zhihuTopicApiClient;
        this.zhihuQuestionApiClient = zhihuQuestionApiClient;
        this.zhihuColumnApiClient = zhihuColumnApiClient;
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

    @Override
    public Optional<Topic> findByZhihuQuestionId(String questionId) {
        Optional<Topic> topic = topicRepository.findFirstByDataIdAndType(questionId, ZHIHU_QUESTION);
        if (!topic.isPresent()) {
            zhihuQuestionApiClient.getQuestionById(questionId);
        }
        return topic;
    }

    private Topic createFromZhihuTopic(ZhihuTopic zhihuTopic) {
        Topic topic = new Topic(zhihuTopic.getName(), ZHIHU_TOPIC, zhihuTopic.getId());
        topic.setZhihuTopic(zhihuTopic);
        return topic;
    }

    private Topic createFromZhihuQuestion(ZhihuQuestion zhihuQuestion) {
        Topic topic = new Topic(zhihuQuestion.getTitle(), ZHIHU_QUESTION, zhihuQuestion.getId());
        topic.setZhihuQuestion(zhihuQuestion);
        return topic;
    }

    @Override
    public void saveZhihuTopicParents(String topicId, Set<ZhihuTopic> parents) {
        Optional<Topic> optional = topicRepository.findFirstByDataIdAndType(topicId, ZHIHU_TOPIC);
        if (optional.isPresent()) {
            Topic topic = optional.get();
            // if exist, update zhihu topic
            ZhihuTopic exist = topic.getZhihuTopic();
            if (exist != null) {
                exist.setParents(parents);
            }
            topicRepository.save(topic);
        } else {
            zhihuTopicApiClient.getTopicById(topicId);
        }
        // saven parents
        parents.forEach(this::save);
    }

    @Override
    public void saveZhihuTopicChildren(String topicId, Set<ZhihuTopic> children) {
        Optional<Topic> optional = topicRepository.findFirstByDataIdAndType(topicId, ZHIHU_TOPIC);
        if (optional.isPresent()) {
            Topic topic = optional.get();
            // if exist, update zhihu topic
            ZhihuTopic exist = topic.getZhihuTopic();
            if (exist != null) {
                exist.setChildren(children);
            }
            zhihuTopicApiClient.getTopicById(topicId);
        } else {
            zhihuTopicApiClient.getTopicById(topicId);
        }
        // saven parents
        children.forEach(this::save);
    }

    @Override
    public Optional<Topic> findByZhihuTopicId(String topicId) {
        Optional<Topic> topic = topicRepository.findFirstByDataIdAndType(topicId, ZHIHU_QUESTION);
        if (!topic.isPresent()) {
            zhihuTopicApiClient.getTopicById(topicId);
        }
        return topic;
    }

    @Override
    public List<Topic> findByName(String name, Pageable pageable) {
        return topicFuzzyRepository.findByNameLike(name, pageable);
    }

    @Override
    public Topic find(ZhihuColumn zhihuColumn) {
        Optional<Topic> exist = topicRepository.findFirstByDataIdAndType(zhihuColumn.getId(), ZHIHU_COLUMN);
        if (exist.isPresent()) {
            return exist.get();
        }
        // if non-exist, create new one with zhihu column
        Topic topic = createFromZhihuColumn(zhihuColumn);
        return this.topicRepository.save(topic);
    }

    private Topic createFromZhihuColumn(ZhihuColumn zhihuColumn) {
        Topic topic = new Topic(zhihuColumn.getTitle(), ZHIHU_COLUMN, zhihuColumn.getId());
        topic.setZhihuColumn(zhihuColumn);
        return topic;
    }

    @Override
    public Topic save(ZhihuColumn zhihuColumn) {
        // find existing topic if any
        Optional<Topic> exist = topicRepository.findFirstByDataIdAndType(zhihuColumn.getId(), ZHIHU_COLUMN);
        if (exist.isPresent()) {
            Topic existTopic = exist.get();
            if (existTopic.getType() == ZHIHU_TOPIC) {
                // if exist, merge with exist
                ZhihuColumn existZhihuColumn = existTopic.getZhihuColumn();
                existTopic.setZhihuColumn(MergeUtils.merge(zhihuColumn, existZhihuColumn));
                return this.topicRepository.save(existTopic);
            }
        }
        // if non-exist, create new one with zhihu topic
        Topic topic = createFromZhihuColumn(zhihuColumn);
        return this.topicRepository.save(topic);
    }

    @Override
    public Optional<Topic> findByZhihuColumnId(String columnId) {
        Optional<Topic> topic = topicRepository.findFirstByDataIdAndType(columnId, ZHIHU_COLUMN);
        if (!topic.isPresent()) {
            zhihuColumnApiClient.getColumnById(columnId);
        }
        return topic;
    }
}
