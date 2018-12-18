package com.nobodyhub.transcendence.zhihu.member.message;

import com.nobodyhub.transcendence.zhihu.api.domain.ZhihuApiMember;
import com.nobodyhub.transcendence.zhihu.member.api.UrlConverter;
import com.nobodyhub.transcendence.zhihu.member.service.ZhihuMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(ZhihuMemberApi.class)
public class KafkaSource {
    private final Source source;
    private final UrlConverter urlConverter;
    private final ZhihuMemberService memberService;

    public KafkaSource(Source source,
                       UrlConverter urlConverter,
                       ZhihuMemberService memberService) {
        this.source = source;
        this.urlConverter = urlConverter;
        this.memberService = memberService;
    }

    public void getByUrlToken(String urlToken) {
        log.info("Sending Kafka message for urlToken: {}.", urlToken);
        String url = this.urlConverter.convert("/v4/members/{urlToken}?include=locations,employments,gender,educations,business,voteup_count,thanked_Count,follower_count,following_count,cover_url,following_topic_count,following_question_count,following_favlists_count,following_columns_count,avatar_hue,answer_count,articles_count,pins_count,question_count,columns_count,commercial_question_count,favorite_count,favorited_count,logs_count,marked_answers_count,marked_answers_text,message_thread_token,account_status,is_active,is_force_renamed,is_bind_sina,sina_weibo_url,sina_weibo_name,show_sina_weibo,is_blocking,is_blocked,is_following,is_followed,mutual_followees_count,vote_to_count,vote_from_count,thank_to_count,thank_from_count,thanked_count,description,hosted_live_count,participated_live_count,allow_message,industry_category,org_name,org_homepage,is_org",
            urlToken);
        ApiRequestMessage message = new ApiRequestMessage(url, "client#method");
        this.source.output().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(ZhihuMemberApi.ZHIHU_MEMBER)
    public void handleResponse(ZhihuApiMember member) {
        this.memberService.save(member);
    }
}
