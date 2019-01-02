package com.nobodyhub.transcendence.zhihu.answer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.answer.domain.ZhihuAnswerList;
import com.nobodyhub.transcendence.zhihu.answer.domain.ZhihuComments;
import com.nobodyhub.transcendence.zhihu.common.client.DeedHubClient;
import com.nobodyhub.transcendence.zhihu.common.cookies.ZhihuApiCookies;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiPaging;
import com.nobodyhub.transcendence.zhihu.common.service.ZhihuApiChannelBaseService;
import com.nobodyhub.transcendence.zhihu.configuration.ZhihuApiProperties;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nobodyhub.transcendence.zhihu.answer.service.ZhihuAnswerApiChannel.*;

@Slf4j
@Service
@EnableBinding(ZhihuAnswerApiChannel.class)
public class ZhihuAnswerApiService extends ZhihuApiChannelBaseService<ZhihuAnswerApiChannel> {
    private final DeedHubClient deedHubClient;

    public ZhihuAnswerApiService(ZhihuAnswerApiChannel channel,
                                 ApiResponseConverter converter,
                                 ApiAsyncExecutor apiAsyncExecutor,
                                 KafkaHeaderHandler headerHandler,
                                 @Qualifier(ZHIHU_ANSWER_REQUEST_CHANNEL) PollableMessageSource requestMessageSource,
                                 ObjectMapper objectMapper,
                                 ZhihuApiProperties apiProperties,
                                 ZhihuApiCookies cookies,
                                 DeedHubClient deedHubClient) {
        super(channel, converter, apiAsyncExecutor, headerHandler, requestMessageSource, objectMapper, apiProperties, cookies);
        this.deedHubClient = deedHubClient;
    }

    /**
     * Get the answer of given id
     *
     * @param answerId
     * @return
     */
    public void getById(String answerId) {
        String urlTemplate = "http://www.zhihu.com/api/v4/answers/{answerId}?include=data%5B%2A%5D.is_normal%2Cis_collapsed%2Ccollapse_reason%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Cmark_infos%2Ccreated_time%2Cupdated_time%2Crelationship.is_authorized%2Cvoting%2Cis_author%2Cis_thanked%2Cis_nothelp%2Cupvoted_followees&limit=100&offset=10&data%5B%2A%5D.author.badge%5B%3F%28type%3Dbest_answerer%29%5D.topics=";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_ANSWER_CALLBACK_ANSWER, urlTemplate, answerId);
        channel.sendAnswerRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_ANSWER_CALLBACK_ANSWER)
    public void receiveAnswer(@Payload byte[] message,
                              @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
        Optional<ZhihuAnswer> answer = converter.convert(message, ZhihuAnswer.class);
        answer.ifPresent(deedHubClient::saveZhihuAnswerNoReturn);
    }

    /**
     * Get the comment of answer with given id
     *
     * @param answerId
     */
    public void getCommentById(String answerId) {
        String urlTemplate = "https://www.zhihu.com/api/v4/answers/{answerId}/root_comments?include=data%5B*%5D.author%2Ccollapsed%2Creply_to_author%2Cdisliked%2Ccontent%2Cvoting%2Cvote_count%2Cis_parent_author%2Cis_author&order=normal&limit=20&offset=0&status=open";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_ANSWER_CALLBACK_COMMENT, urlTemplate, answerId);
        channel.sendAnswerRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_ANSWER_CALLBACK_COMMENT)
    public void receiveComments(@Payload byte[] message,
                                @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
        Optional<ZhihuComments> comments = converter.convert(message, ZhihuComments.class);
        if (comments.isPresent()) {
            List<ZhihuComment> commentList = comments.get().getData();
            if (commentList != null && !commentList.isEmpty()) {
                for (ZhihuComment comment : commentList) {
                    deedHubClient.saveZhihuCommentNoReturn(comment);
                }
                // follow paging to ge more comments
                ZhihuApiPaging paging = comments.get().getPaging();
                if (paging != null
                    && paging.getNext() != null
                    && !paging.getIsEnd()) {
                    ApiRequestMessage next = new ApiRequestMessage(IN_ZHIHU_ANSWER_CALLBACK_COMMENT, paging.getNext());
                    channel.sendAnswerRequest().send(MessageBuilder.withPayload(next).build());
                }
            }
        }
    }

    /**
     * Get answers for member with given urlToken
     *
     * @param urlToken
     */
    public void getByMember(String urlToken) {
        getByMember(urlToken, "0");
    }

    @StreamListener(IN_ZHIHU_ANSWER_CALLBACK_MEMBER_ANSWER)
    public void receiveMemberAnswer(@Payload byte[] message,
                                    @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
        Optional<ZhihuAnswerList> zhihuAnswerList = converter.convert(message, ZhihuAnswerList.class);
        zhihuAnswerList.ifPresent((answerList) -> {
            ZhihuApiPaging paging = answerList.getPaging();
            List<ZhihuAnswer> data = answerList.getData();
            if (data != null && !data.isEmpty()) {
                for (ZhihuAnswer answer : data) {
                    deedHubClient.saveZhihuAnswerNoReturn(answer);
                }
                if (paging != null && !paging.getIsEnd()) {
                    // the url in paging.getNext() is invalid
                    Optional<ApiRequestMessage> originMsg = headerHandler.getOriginRequest(messageHeaders);
                    originMsg.ifPresent(msg -> getByMember(msg.getUrlVariables()[0], msg.getUrlVariables()[1]));
                }
            }
        });
    }

    private void getByMember(String urlToken, String offset) {
        String urlTemplate = "https://www.zhihu.com/api/v4/members/{urlToken}/answers?sort_by=default&include=data%5B%2A%5D.is_normal%2Cis_collapsed%2Ccollapse_reason%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Cmark_infos%2Ccreated_time%2Cupdated_time%2Crelationship.is_authorized%2Cvoting%2Cis_author%2Cis_thanked%2Cis_nothelp%2Cupvoted_followees&limit=20&offset={offset}&data%5B%2A%5D.author.badge%5B%3F%28type%3Dbest_answerer%29%5D.topics=";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_ANSWER_CALLBACK_MEMBER_ANSWER,
            urlTemplate,
            urlToken,
            offset);
        channel.sendAnswerRequest().send(MessageBuilder.withPayload(message).build());
    }

    public void getByQuestion(String questionId) {
        String urlTemplate = "https://www.zhihu.com/api/v4/questions/60531155/answers?data%5B%2A%5D.author.follower_count%2Cbadge%5B%2A%5D.topics=&data%5B%2A%5D.mark_infos%5B%2A%5D.url=&include=data%5B%2A%5D.is_normal%2Cadmin_closed_comment%2Creward_info%2Cis_collapsed%2Cannotation_action%2Cannotation_detail%2Ccollapse_reason%2Cis_sticky%2Ccollapsed_by%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Ceditable_content%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Ccreated_time%2Cupdated_time%2Creview_info%2Crelevant_info%2Cquestion%2Cexcerpt%2Crelationship.is_authorized%2Cis_author%2Cvoting%2Cis_thanked%2Cis_nothelp%2Cis_labeled&limit=20&offset=0";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_ANSWER_CALLBACK_MEMBER_ANSWER,
            urlTemplate,
            questionId);
        channel.sendAnswerRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_ANSWER_CALLBACK_QUESTION_ANSWER)
    public void receiveQuestionAnswer(@Payload byte[] message,
                                      @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
        Optional<ZhihuAnswerList> zhihuAnswerList = converter.convert(message, ZhihuAnswerList.class);
        zhihuAnswerList.ifPresent((answerList) -> {
            ZhihuApiPaging paging = answerList.getPaging();
            List<ZhihuAnswer> data = answerList.getData();
            if (data != null && !data.isEmpty()) {
                for (ZhihuAnswer answer : data) {
                    deedHubClient.saveZhihuAnswerNoReturn(answer);
                }
                if (paging != null
                    && !paging.getIsEnd()
                    && paging.getNext() != null) {
                    channel.sendAnswerRequest().send(MessageBuilder.withPayload(
                        new ApiRequestMessage(IN_ZHIHU_ANSWER_CALLBACK_COMMENT, paging.getNext())
                    ).build());
                }
            }
        });
    }
}
