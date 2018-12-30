package com.nobodyhub.transcendence.zhihu.answer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.common.cookies.ZhihuApiCookies;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiAnswer;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiAnswerComment;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiAnswerComments;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiPaging;
import com.nobodyhub.transcendence.zhihu.common.service.ApiChannelBaseService;
import com.nobodyhub.transcendence.zhihu.configuration.ZhihuApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
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
public class ZhihuAnswerApiService extends ApiChannelBaseService<ZhihuAnswerApiChannel> {

    public ZhihuAnswerApiService(ZhihuAnswerApiChannel channel,
                                 ApiResponseConverter converter,
                                 ObjectMapper objectMapper,
                                 ZhihuApiProperties apiProperties,
                                 ApiAsyncExecutor apiAsyncExecutor,
                                 ZhihuApiCookies cookies,
                                 KafkaHeaderHandler headerHandler) {
        super(channel, converter, objectMapper, apiProperties, apiAsyncExecutor, cookies, headerHandler);
    }

    /**
     * Retrieve message from queue of outbound request
     *
     * @param message contents of request
     * @throws InterruptedException
     */
    @StreamListener(ZHIHU_ANSWER_REQUEST_CHANNEL)
    public void receiveAnswerRequest(ApiRequestMessage message) throws InterruptedException {
        makeOutboundRequest(message);
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
        Optional<ZhihuApiAnswer> answer = converter.convert(message, ZhihuApiAnswer.class);
        if (answer.isPresent()) {
            //TODO: save as deed
        }
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

    public void receiveComments(@Payload byte[] message,
                                @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
        Optional<ZhihuApiAnswerComments> comments = converter.convert(message, ZhihuApiAnswerComments.class);
        if (comments.isPresent()) {
            List<ZhihuApiAnswerComment> commentList = comments.get().getData();
            if (commentList != null && !commentList.isEmpty()) {
                for (ZhihuApiAnswerComment comment : commentList) {
                    //TODO: save as deed
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
}
