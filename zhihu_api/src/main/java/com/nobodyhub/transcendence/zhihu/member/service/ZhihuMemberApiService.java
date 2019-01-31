package com.nobodyhub.transcendence.zhihu.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.common.client.PeopleHubClient;
import com.nobodyhub.transcendence.zhihu.common.service.ZhihuApiChannelBaseService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nobodyhub.transcendence.zhihu.member.service.ZhihuMemberApiChannel.IN_ZHIHU_MEMBER_CALLBACK_MEMBER;

@Slf4j
@Service
@EnableBinding(ZhihuMemberApiChannel.class)
public class ZhihuMemberApiService extends ZhihuApiChannelBaseService<ZhihuMemberApiChannel> {

    private final PeopleHubClient peopleHubClient;

    protected ZhihuMemberApiService(ZhihuMemberApiChannel channel,
                                    ApiResponseConverter converter,
                                    ApiAsyncExecutor apiAsyncExecutor,
                                    KafkaHeaderHandler headerHandler,
                                    ObjectMapper objectMapper,
                                    PeopleHubClient peopleHubClient) {
        super(channel, converter, apiAsyncExecutor, headerHandler, objectMapper);
        this.peopleHubClient = peopleHubClient;
    }

    /**
     * Send request to fetch member information
     *
     * @param urlToken {@link ZhihuMember#getUrlToken()}
     */
    public void getMember(String urlToken) {
        String url = "https://www.zhihu.com/api/v4/members/{urlToken}?include=locations,employments,gender,educations,business,voteup_count,thanked_Count,follower_count,following_count,cover_url,following_topic_count,following_question_count,following_favlists_count,following_columns_count,avatar_hue,answer_count,articles_count,pins_count,question_count,columns_count,commercial_question_count,favorite_count,favorited_count,logs_count,marked_answers_count,marked_answers_text,message_thread_token,account_status,is_active,is_force_renamed,is_bind_sina,sina_weibo_url,sina_weibo_name,show_sina_weibo,is_blocking,is_blocked,is_following,is_followed,mutual_followees_count,vote_to_count,vote_from_count,thank_to_count,thank_from_count,thanked_count,description,hosted_live_count,participated_live_count,allow_message,industry_category,org_name,org_homepage,is_org";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_MEMBER_CALLBACK_MEMBER, url, urlToken);
        channel.sendMemberRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_MEMBER_CALLBACK_MEMBER)
    public void receiveMember(@Payload byte[] message) {
        Optional<ZhihuMember> member = converter.convert(message, ZhihuMember.class);
        member.ifPresent(peopleHubClient::saveZhihuMemberNoReturn);
    }
}
