package com.nobodyhub.transcendence.fetcher.service.impl;

import com.nobodyhub.transcendence.fetcher.domain.ZhihuAuthor;
import com.nobodyhub.transcendence.fetcher.domain.ZhihuQuestion;
import com.nobodyhub.transcendence.fetcher.service.ZhihuApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@ConditionalOnProperty(name = "zhihu.api.version", havingValue = "4")
@Service
public class ZhihuApiServiceImplV4 implements ZhihuApiService {
    private final int limit;
    private final String sortBy;
    private final RestTemplate restTemplate;


    @Autowired
    public ZhihuApiServiceImplV4(RestTemplate restTemplate,
                                 @Value("${zhihu.api.limit}") int limit,
                                 @Value("${zhihu.api.sortBy}") String sortBy) {
        this.limit = limit;
        this.sortBy = sortBy;
        this.restTemplate = restTemplate;
    }

    @Override
    public ZhihuQuestion getAnswersForQuestion(String questionId, int offset) {
        ZhihuQuestion question = this.restTemplate.getForObject(
            String.format("/api/v4/questions/%s/answers?include=data[*].is_normal,admin_closed_comment,reward_info,is_collapsed,annotation_action,annotation_detail,collapse_reason,is_sticky,collapsed_by,suggest_edit,comment_count,can_comment,content,editable_content,voteup_count,reshipment_settings,comment_permission,created_time,updated_time,review_info,relevant_info,question,excerpt,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp,is_labeled;data[*].mark_infos[*].url;data[*].author.follower_count,badge[*].topics" +
                    "&limit=%d" +
                    "&offset=%d" +
                    "&sort_by=%s",
                questionId, limit, offset, sortBy), ZhihuQuestion.class);
        return question;
    }

    @Override
    public ZhihuAuthor getAuthor(String urlToken) {
        return this.restTemplate.getForObject(
            "/api/v4/members/{urlToken}?include=allow_message,is_followed,is_following,is_org,is_blocking,employments,answer_count,follower_count,articles_count,gender,badge[*].topics",
            ZhihuAuthor.class,
            urlToken);
    }
}
