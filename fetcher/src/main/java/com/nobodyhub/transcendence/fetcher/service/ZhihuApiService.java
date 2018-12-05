package com.nobodyhub.transcendence.fetcher.service;


import com.nobodyhub.transcendence.fetcher.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuAuthor;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuQuestion;

import java.util.List;

public interface ZhihuApiService {
    /**
     * Get the answer list for question with given question
     *
     * @param questionId question id
     * @return
     */
    List<ZhihuAnswer> getAnswersForQuestion(String questionId);

    /**
     * Get Author information
     *
     * @param urlToken unique token in the url
     * @return
     */
    ZhihuAuthor getAuthor(String urlToken);
}
