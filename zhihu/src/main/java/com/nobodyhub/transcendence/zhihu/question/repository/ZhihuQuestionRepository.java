package com.nobodyhub.transcendence.zhihu.question.repository;

import com.nobodyhub.transcendence.zhihu.api.domain.ZhihuApiQuestion;
import org.springframework.data.repository.CrudRepository;

public interface ZhihuQuestionRepository extends CrudRepository<ZhihuApiQuestion, String> {
}
