package com.nobodyhub.transcendence.zhihu.question.service;


import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.dto.ZhihuMember;

import java.util.List;

public interface ZhihuApiService {
    /**
     * Get the answer list for question with given id
     *
     * @param questionId question id
     * @return
     */
    List<ZhihuAnswer> getAnswersForQuestion(String questionId);

    /**
     * Get the answer list for member with given token
     *
     * @param token member url token
     * @return
     */
    List<ZhihuAnswer> getAnswersForMember(String token);

    /**
     * Get Author information
     *
     * @param urlToken unique token in the url
     * @return
     */
    ZhihuMember getAuthor(String urlToken);
}
