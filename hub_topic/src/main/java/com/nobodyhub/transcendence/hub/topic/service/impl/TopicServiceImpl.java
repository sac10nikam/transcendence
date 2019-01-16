package com.nobodyhub.transcendence.hub.topic.service.impl;

import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.domain.excerpt.TopicDataExcerpt;
import com.nobodyhub.transcendence.hub.topic.client.ZhihuColumnApiClient;
import com.nobodyhub.transcendence.hub.topic.client.ZhihuQuestionApiClient;
import com.nobodyhub.transcendence.hub.topic.repository.TopicFuzzyRepository;
import com.nobodyhub.transcendence.hub.topic.repository.TopicRepository;
import com.nobodyhub.transcendence.hub.topic.service.TopicService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuColumn;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nobodyhub.transcendence.hub.domain.Topic.TopicType.ZHIHU_COLUMN;
import static com.nobodyhub.transcendence.hub.domain.Topic.TopicType.ZHIHU_QUESTION;

@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicFuzzyRepository topicFuzzyRepository;

    private final ZhihuQuestionApiClient zhihuQuestionApiClient;
    private final ZhihuColumnApiClient zhihuColumnApiClient;

    public TopicServiceImpl(TopicRepository topicRepository,
                            TopicFuzzyRepository topicFuzzyRepository,
                            ZhihuQuestionApiClient zhihuQuestionApiClient,
                            ZhihuColumnApiClient zhihuColumnApiClient) {
        this.topicRepository = topicRepository;
        this.topicFuzzyRepository = topicFuzzyRepository;
        this.zhihuQuestionApiClient = zhihuQuestionApiClient;
        this.zhihuColumnApiClient = zhihuColumnApiClient;
    }

    @Override
    public Topic find(ZhihuQuestion zhihuQuestion) {
        Optional<Topic> exist = topicRepository.findFirstByExcerptsContains(TopicDataExcerpt.of(ZHIHU_QUESTION, zhihuQuestion.getId()));
        if (exist.isPresent()) {
            return exist.get();
        }
        // if non-exist, create new one with zhihu topic
        Topic topic = createFromZhihuQuestion(zhihuQuestion);
        return this.topicRepository.save(topic);
    }

    @Override
    public Topic save(ZhihuQuestion zhihuQuestion) {
        // find existing topic if any
        Optional<Topic> exist = topicRepository.findFirstByExcerptsContains(TopicDataExcerpt.of(ZHIHU_QUESTION, zhihuQuestion.getId()));
        if (exist.isPresent()) {
            Topic existTopic = exist.get();
            // if exist, merge with exist
            ZhihuQuestion existZhihuQuestion = existTopic.getZhihuQuestion();
            existTopic.setZhihuQuestion(MergeUtils.merge(zhihuQuestion, existZhihuQuestion));
            return this.topicRepository.save(existTopic);

        }
        // if non-exist, create new one with zhihu topic
        Topic topic = createFromZhihuQuestion(zhihuQuestion);
        return this.topicRepository.save(topic);
    }

    @Override
    public Optional<Topic> findByZhihuQuestionId(String questionId) {
        Optional<Topic> topic = topicRepository.findFirstByExcerptsContains(TopicDataExcerpt.of(ZHIHU_QUESTION, questionId));
        if (!topic.isPresent()) {
            zhihuQuestionApiClient.getQuestionById(questionId);
        }
        return topic;
    }

    private Topic createFromZhihuQuestion(ZhihuQuestion zhihuQuestion) {
        Topic topic = new Topic();
        topic.setName(zhihuQuestion.getTitle());
        topic.addExcerpt(TopicDataExcerpt.of(ZHIHU_QUESTION, zhihuQuestion.getId()));
        topic.setZhihuQuestion(zhihuQuestion);
        return topic;
    }

    @Override
    public Page<Topic> findByName(String name, Pageable pageable) {
        return topicFuzzyRepository.findByNameLike(name, pageable);
    }

    @Override
    public Topic find(ZhihuColumn zhihuColumn) {
        Optional<Topic> exist = topicRepository.findFirstByExcerptsContains(TopicDataExcerpt.of(ZHIHU_COLUMN, zhihuColumn.getId()));
        if (exist.isPresent()) {
            return exist.get();
        }
        // if non-exist, create new one with zhihu column
        Topic topic = createFromZhihuColumn(zhihuColumn);
        return this.topicRepository.save(topic);
    }

    private Topic createFromZhihuColumn(ZhihuColumn zhihuColumn) {
        Topic topic = new Topic();
        topic.setName(zhihuColumn.getTitle());
        topic.addExcerpt(TopicDataExcerpt.of(ZHIHU_COLUMN, zhihuColumn.getId()));
        topic.setZhihuColumn(zhihuColumn);
        return topic;
    }

    @Override
    public Topic save(ZhihuColumn zhihuColumn) {
        // find existing topic if any
        Optional<Topic> exist = topicRepository.findFirstByExcerptsContains(TopicDataExcerpt.of(ZHIHU_COLUMN, zhihuColumn.getId()));
        if (exist.isPresent()) {
            Topic existTopic = exist.get();
            // if exist, merge with exist
            ZhihuColumn existZhihuColumn = existTopic.getZhihuColumn();
            existTopic.setZhihuColumn(MergeUtils.merge(zhihuColumn, existZhihuColumn));
            return this.topicRepository.save(existTopic);

        }
        // if non-exist, create new one with zhihu topic
        Topic topic = createFromZhihuColumn(zhihuColumn);
        return this.topicRepository.save(topic);
    }

    @Override
    public Optional<Topic> findByZhihuColumnId(String columnId) {
        Optional<Topic> topic = topicRepository.findFirstByExcerptsContains(TopicDataExcerpt.of(ZHIHU_COLUMN, columnId));
        if (!topic.isPresent()) {
            zhihuColumnApiClient.getColumnById(columnId);
        }
        return topic;
    }
}
