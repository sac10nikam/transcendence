package com.nobodyhub.transcendence.zhihu.answer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.cookies.ApiCookies;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.answer.domain.ZhihuAnswerList;
import com.nobodyhub.transcendence.zhihu.common.client.DeedHubClient;
import com.nobodyhub.transcendence.zhihu.common.configuration.ZhihuApiProperties;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiPaging;
import com.nobodyhub.transcendence.zhihu.common.service.ZhihuApiChannelBaseService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
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
                                 ApiCookies cookies,
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
        this.cookies.extract(messageHeaders);
        Optional<ZhihuAnswer> answer = converter.convert(message, ZhihuAnswer.class);
        answer.ifPresent(deedHubClient::saveZhihuAnswerNoReturn);
    }

    /**
     * Get answers for member with given urlToken
     *
     * @param urlToken
     */
    public void getByMember(String urlToken) {
        getByMember(urlToken, 0);
    }

    private void getByMember(String urlToken, int offset) {
        String urlTemplate = "https://www.zhihu.com/api/v4/members/{urlToken}/answers?sort_by=default&include=data%5B%2A%5D.is_normal%2Cis_collapsed%2Ccollapse_reason%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Cmark_infos%2Ccreated_time%2Cupdated_time%2Crelationship.is_authorized%2Cvoting%2Cis_author%2Cis_thanked%2Cis_nothelp%2Cupvoted_followees&limit=20&offset={offset}&data%5B%2A%5D.author.badge%5B%3F%28type%3Dbest_answerer%29%5D.topics=";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_ANSWER_CALLBACK_MEMBER_ANSWER,
            urlTemplate,
            urlToken,
            String.valueOf(offset));
        channel.sendAnswerRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_ANSWER_CALLBACK_MEMBER_ANSWER)
    public void receiveMemberAnswer(@Payload byte[] message,
                                    @Headers MessageHeaders messageHeaders) {
        this.cookies.extract(messageHeaders);
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
                    originMsg.ifPresent(msg -> getByMember(msg.getUrlVariables()[0],
                        Integer.valueOf(msg.getUrlVariables()[1]) + data.size()));
                }
            }
        });
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
        this.cookies.extract(messageHeaders);
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
                        new ApiRequestMessage(IN_ZHIHU_ANSWER_CALLBACK_QUESTION_ANSWER, paging.getNext())
                    ).build());
                }
            }
        });
    }
}
