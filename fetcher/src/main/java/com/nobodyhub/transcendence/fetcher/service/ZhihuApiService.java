package com.nobodyhub.transcendence.fetcher.service;


import com.nobodyhub.transcendence.fetcher.domain.ZhihuAuthor;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuQuestion;

public interface ZhihuApiService {
    /**
     * Get the answer list for question with given question
     *
     * @param questionId question id
     * @param offset     the offset start
     * @return
     */
    ZhihuQuestion getAnswersForQuestion(String questionId, int offset);

    /**
     * Get Author information
     *
     * @param urlToken unique token in the url
     * @return
     */
    ZhihuAuthor getAuthor(String urlToken);
}
