package com.nobodyhub.transcendence.zhihu.api.member.service.impl;

import com.nobodyhub.transcendence.zhihu.api.common.service.ApiRequestService;
import com.nobodyhub.transcendence.zhihu.api.domain.ZhihuApiMember;
import com.nobodyhub.transcendence.zhihu.api.member.service.ZhihuMemberApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ZhihuMemberApiServiceImpl implements ZhihuMemberApiService {
    private final ApiRequestService apiRequestService;

    public ZhihuMemberApiServiceImpl(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    @Override
    public Optional<ZhihuApiMember> getByUrlToken(String urlToken) {
        return apiRequestService.doGet("/v4/members/{urlToken}?include=locations,employments,gender,educations,business,voteup_count,thanked_Count,follower_count,following_count,cover_url,following_topic_count,following_question_count,following_favlists_count,following_columns_count,avatar_hue,answer_count,articles_count,pins_count,question_count,columns_count,commercial_question_count,favorite_count,favorited_count,logs_count,marked_answers_count,marked_answers_text,message_thread_token,account_status,is_active,is_force_renamed,is_bind_sina,sina_weibo_url,sina_weibo_name,show_sina_weibo,is_blocking,is_blocked,is_following,is_followed,mutual_followees_count,vote_to_count,vote_from_count,thank_to_count,thank_from_count,thanked_count,description,hosted_live_count,participated_live_count,allow_message,industry_category,org_name,org_homepage,is_org",
            ZhihuApiMember.class,
            urlToken);
    }
}
