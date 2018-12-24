package com.nobodyhub.transcendence.zhihu.question.service.impl;

import com.nobodyhub.transcendence.common.merge.MergeUtils;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiQuestion;
import com.nobodyhub.transcendence.zhihu.question.repository.ZhihuQuestionRepository;
import com.nobodyhub.transcendence.zhihu.question.service.ZhihuQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class ZhihuQuestionServiceImpl implements ZhihuQuestionService {

    private final ZhihuQuestionRepository repository;

    public ZhihuQuestionServiceImpl(ZhihuQuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ZhihuApiQuestion> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public ZhihuApiQuestion save(ZhihuApiQuestion question) {
        Assert.notNull(question, "The Question to be save can not be null!");
        Assert.notNull(question.getId(), "The Question should not have null id field!");
        Optional<ZhihuApiQuestion> existed = findById(question.getId());
        return existed.map(zhihuQuestion ->
            repository.save(MergeUtils.merge(question, zhihuQuestion))
        ).orElseGet(() -> repository.save(question));
    }
}
