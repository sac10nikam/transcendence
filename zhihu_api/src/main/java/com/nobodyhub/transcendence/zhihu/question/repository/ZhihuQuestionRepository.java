package com.nobodyhub.transcendence.zhihu.question.repository;

import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiQuestion;
import org.springframework.data.repository.CrudRepository;

public interface ZhihuQuestionRepository extends CrudRepository<ZhihuApiQuestion, String> {
}
