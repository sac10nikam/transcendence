package com.nobodyhub.transcendence.zhihu.member.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuMember;
import com.nobodyhub.transcendence.zhihu.member.service.ZhihuMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Slf4j
@Component
public class KafkaSource {
    private final Source source;
    private final ZhihuMemberService memberService;
    private final ObjectMapper objectMapper;

    public KafkaSource(Source source,
                       ZhihuMemberService memberService,
                       ObjectMapper objectMapper) {
        this.source = source;
        this.memberService = memberService;
        this.objectMapper = objectMapper;
    }

    public void getByUrlToken(String urlToken) {
        log.info("Sending Kafka message for urlToken: {}.", urlToken);
        String url = "https://www.zhihu.com/v4/members/{urlToken}?include=locations,employments,gender,educations,business,voteup_count,thanked_Count,follower_count,following_count,cover_url,following_topic_count,following_question_count,following_favlists_count,following_columns_count,avatar_hue,answer_count,articles_count,pins_count,question_count,columns_count,commercial_question_count,favorite_count,favorited_count,logs_count,marked_answers_count,marked_answers_text,message_thread_token,account_status,is_active,is_force_renamed,is_bind_sina,sina_weibo_url,sina_weibo_name,show_sina_weibo,is_blocking,is_blocked,is_following,is_followed,mutual_followees_count,vote_to_count,vote_from_count,thank_to_count,thank_from_count,thanked_count,description,hosted_live_count,participated_live_count,allow_message,industry_category,org_name,org_homepage,is_org";
        ApiRequestMessage message = new ApiRequestMessage(ZhihuMemberApi.ZHIHU_MEMBER, url, urlToken);
        this.source.output().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(ZhihuMemberApi.ZHIHU_MEMBER)
    public void handleResponse(@Payload byte[] message) {
        log.info("Message received from chennel: {}.", ZhihuMemberApi.ZHIHU_MEMBER);
        try (ByteArrayInputStream ios = new ByteArrayInputStream(message)) {
            //https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
            ObjectInput in = new ObjectInputStream(ios);
            byte[] object = (byte[]) in.readObject();
            ZhihuMember member = objectMapper.readValue(object, ZhihuMember.class);
            log.info("Saving member[{}]...", member.getUrlToken());
            this.memberService.save(member);
            log.info("Save successfully!");
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error happends when reading message.", e);
        }
    }
}
