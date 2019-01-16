package com.nobodyhub.transcendence.hub.tag.service.impl;

import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.hub.domain.Topic;
import com.nobodyhub.transcendence.hub.domain.excerpt.TopicDataExcerpt;
import com.nobodyhub.transcendence.hub.tag.client.ZhihuTopicApiClient;
import com.nobodyhub.transcendence.hub.tag.repository.TagFuzzyRepository;
import com.nobodyhub.transcendence.hub.tag.repository.TagRepository;
import com.nobodyhub.transcendence.hub.tag.service.TagService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.nobodyhub.transcendence.hub.domain.Topic.TopicType.ZHIHU_TOPIC;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagFuzzyRepository tagFuzzyRepository;

    private final ZhihuTopicApiClient zhihuTopicApiClient;

    public TagServiceImpl(TagRepository tagRepository,
                          TagFuzzyRepository tagFuzzyRepository,
                          ZhihuTopicApiClient zhihuTopicApiClient) {
        this.tagRepository = tagRepository;
        this.tagFuzzyRepository = tagFuzzyRepository;
        this.zhihuTopicApiClient = zhihuTopicApiClient;
    }

    @Override
    public Topic find(ZhihuTopic zhihuTopic) {
        Optional<Topic> exist = tagRepository.findFirstByExcerptsContains(TopicDataExcerpt.of(ZHIHU_TOPIC, zhihuTopic.getId()));
        if (exist.isPresent()) {
            return exist.get();
        }
        // if non-exist, create new one with zhihu topic
        Topic topic = createFromZhihuTopic(zhihuTopic);
        return this.tagRepository.save(topic);
    }

    @Override
    public Topic save(ZhihuTopic zhihuTopic) {
        // find existing topic if any
        Optional<Topic> exist = tagRepository.findFirstByExcerptsContains(TopicDataExcerpt.of(ZHIHU_TOPIC, zhihuTopic.getId()));
        if (exist.isPresent()) {
            Topic existTopic = exist.get();
            // if exist, merge with exist
            ZhihuTopic existZhihuTopic = existTopic.getZhihuTopic();
            existTopic.setZhihuTopic(MergeUtils.merge(zhihuTopic, existZhihuTopic));
            return this.tagRepository.save(existTopic);
        }
        // if non-exist, create new one with zhihu topic
        Topic topic = createFromZhihuTopic(zhihuTopic);
        return this.tagRepository.save(topic);
    }

    private Topic createFromZhihuTopic(ZhihuTopic zhihuTopic) {
        TopicDataExcerpt excerpt = TopicDataExcerpt.of(ZHIHU_TOPIC, zhihuTopic.getId());
        Topic topic = new Topic();
        topic.addExcerpt(excerpt);
        topic.setName(zhihuTopic.getName());
        topic.setZhihuTopic(zhihuTopic);
        return topic;
    }

    @Override
    public void saveZhihuTopicParents(String topicId, Set<ZhihuTopic> parents) {
        Optional<Topic> optional = tagRepository.findFirstByExcerptsContains(TopicDataExcerpt.of(ZHIHU_TOPIC, topicId));
        if (optional.isPresent()) {
            Topic topic = optional.get();
            // if exist, update zhihu topic
            ZhihuTopic exist = topic.getZhihuTopic();
            if (exist != null) {
                exist.setParents(parents);
            }
            tagRepository.save(topic);
        } else {
            zhihuTopicApiClient.getTopicById(topicId);
        }
        // saven parents
        parents.forEach(this::save);
    }

    @Override
    public void saveZhihuTopicChildren(String topicId, Set<ZhihuTopic> children) {
        Optional<Topic> optional = tagRepository.findFirstByExcerptsContains(TopicDataExcerpt.of(ZHIHU_TOPIC, topicId));
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
        Optional<Topic> topic = tagRepository.findFirstByExcerptsContains(TopicDataExcerpt.of(ZHIHU_TOPIC, topicId));
        if (!topic.isPresent()) {
            zhihuTopicApiClient.getTopicById(topicId);
        }
        return topic;
    }

    @Override
    public Page<Topic> findByName(String name, Pageable pageable) {
        return tagFuzzyRepository.findByNameLike(name, pageable);
    }
}
