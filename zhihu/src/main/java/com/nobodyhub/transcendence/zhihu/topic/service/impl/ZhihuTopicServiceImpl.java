package com.nobodyhub.transcendence.zhihu.topic.service.impl;

import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.topic.repository.ZhihuTopicRepository;
import com.nobodyhub.transcendence.zhihu.topic.service.ZhihuTopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ZhihuTopicServiceImpl implements ZhihuTopicService {
    private final ZhihuTopicRepository repository;

    public ZhihuTopicServiceImpl(ZhihuTopicRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ZhihuTopic> getById(String id) {
        Assert.notNull(id, "The id can not be null!");
        return repository.findById(id);
    }

    @Override
    public List<ZhihuTopic> getByName(String name) {
        return repository.findAllByName(name);
    }

    @Override
    public ZhihuTopic save(ZhihuTopic topic) {
        Assert.notNull(topic, "Topic to be saved can not be null!");
        Assert.notNull(topic.getId(), "Topic to be saved should have non-null [id] field");
        return repository.save(topic);
    }
}
