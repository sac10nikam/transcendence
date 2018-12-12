package com.nobodyhub.transcendence.zhihu.question.repository;

import com.nobodyhub.transcendence.zhihu.api.domain.ZhihuQuestion;
import org.springframework.data.repository.CrudRepository;

public interface ZhihuQuestionRepository extends CrudRepository<ZhihuQuestion, String> {
}
