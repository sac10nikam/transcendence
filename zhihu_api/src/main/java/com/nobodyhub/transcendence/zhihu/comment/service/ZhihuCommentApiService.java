package com.nobodyhub.transcendence.zhihu.comment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.common.client.DeedHubClient;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuComments;
import com.nobodyhub.transcendence.zhihu.common.service.ZhihuApiChannelBaseService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

import static com.nobodyhub.transcendence.zhihu.comment.service.ZhihuCommentApiChannel.*;

@Slf4j
@Service
@EnableBinding(ZhihuCommentApiChannel.class)
public class ZhihuCommentApiService extends ZhihuApiChannelBaseService<ZhihuCommentApiChannel> {
    private final DeedHubClient deedHubClient;

    public ZhihuCommentApiService(ZhihuCommentApiChannel channel,
                                  ApiResponseConverter converter,
                                  ApiAsyncExecutor apiAsyncExecutor,
                                  KafkaHeaderHandler headerHandler,
                                  ObjectMapper objectMapper,
                                  DeedHubClient deedHubClient) {
        super(channel, converter, apiAsyncExecutor, headerHandler, objectMapper);
        this.deedHubClient = deedHubClient;
    }

    /**
     * Get the comment of answer with given id
     *
     * @param answerId
     */
    public void getAnswerComments(String answerId) {
        getAnswerComments(answerId, 0);
    }

    private void getAnswerComments(String answerId, int offset) {
        String urlTemplate = "https://www.zhihu.com/api/v4/answers/{answerId}/root_comments?include=data%5B*%5D.author%2Ccollapsed%2Creply_to_author%2Cdisliked%2Ccontent%2Cvoting%2Cvote_count%2Cis_parent_author%2Cis_author&order=normal&limit=20&offset={offset}&status=open";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_COMMENT_CALLBACK_ANSWER_COMMENT, urlTemplate, answerId, String.valueOf(offset));
        channel.sendRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_COMMENT_CALLBACK_ANSWER_COMMENT)
    public void receiveAnswerComments(@Payload byte[] message,
                                      @Headers MessageHeaders messageHeaders) {
        Optional<ZhihuComments> comments = converter.convert(message, ZhihuComments.class);
        Optional<Offset> offset = getOffset(messageHeaders);
        if (comments.isPresent() && offset.isPresent()) {
            List<ZhihuComment> commentList = prepareZhihuComments(comments.get(), offset.get().getParentId());
            if (commentList != null && !commentList.isEmpty()) {
                for (ZhihuComment comment : commentList) {
                    deedHubClient.saveZhihuCommentNoReturn(comment);
                }
                // follow paging to ge more comments
                getAnswerComments(offset.get().getParentId(), offset.get().getOffset() + commentList.size());
            }
        }
    }

    /**
     * Get the comment of article with given id
     *
     * @param articleId
     */
    public void getArticleComments(String articleId) {
        getArticleComments(articleId, 0);
    }

    private void getArticleComments(String articleId, int offset) {
        String urlTemplate = "https://www.zhihu.com/api/v4/articles/{articleId}/root_comments?include=data%5B*%5D.author%2Ccollapsed%2Creply_to_author%2Cdisliked%2Ccontent%2Cvoting%2Cvote_count%2Cis_parent_author%2Cis_author&order=normal&limit=20&offset={offset}&status=open";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_COMMENT_CALLBACK_ARTICLE_COMMENT, urlTemplate, articleId, String.valueOf(offset));
        channel.sendRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_COMMENT_CALLBACK_ARTICLE_COMMENT)
    public void receiveArticleComments(@Payload byte[] message,
                                       @Headers MessageHeaders messageHeaders) {
        Optional<ZhihuComments> comments = converter.convert(message, ZhihuComments.class);
        Optional<Offset> offset = getOffset(messageHeaders);
        if (comments.isPresent() && offset.isPresent()) {
            List<ZhihuComment> commentList = prepareZhihuComments(comments.get(), offset.get().getParentId());
            if (commentList != null && !commentList.isEmpty()) {
                for (ZhihuComment comment : commentList) {
                    deedHubClient.saveZhihuCommentNoReturn(comment);
                }
                // follow paging to ge more comments
                getArticleComments(offset.get().getParentId(), offset.get().getOffset() + commentList.size());
            }
        }
    }

    /**
     * Get the comment of question with given id
     *
     * @param questionid
     */
    public void getQuestionComments(String questionid) {
        getQuestionComments(questionid, 0);
    }

    private void getQuestionComments(String questionid, int offset) {
        String urlTemplate = "https://www.zhihu.com/api/v4/questions/{questionid}/root_comments?include=data%5B*%5D.author%2Ccollapsed%2Creply_to_author%2Cdisliked%2Ccontent%2Cvoting%2Cvote_count%2Cis_parent_author%2Cis_author&order=normal&limit=20&offset={offset}&status=open";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_COMMENT_CALLBACK_QUESTION_COMMENT, urlTemplate, questionid, String.valueOf(offset));
        channel.sendRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_COMMENT_CALLBACK_QUESTION_COMMENT)
    public void receiveQuestionComments(@Payload byte[] message,
                                        @Headers MessageHeaders messageHeaders) {
        Optional<ZhihuComments> comments = converter.convert(message, ZhihuComments.class);
        Optional<Offset> offset = getOffset(messageHeaders);
        if (comments.isPresent() && offset.isPresent()) {
            List<ZhihuComment> commentList = prepareZhihuComments(comments.get(), offset.get().getParentId());
            if (commentList != null && !commentList.isEmpty()) {
                for (ZhihuComment comment : commentList) {
                    deedHubClient.saveZhihuCommentNoReturn(comment);
                }
                // follow paging to ge more comments
                getQuestionComments(offset.get().getParentId(), offset.get().getOffset() + commentList.size());
            }
        }
    }

    /**
     * Parse parent id and previous offset from orginal request
     *
     * @param messageHeaders
     * @return
     */
    private Optional<Offset> getOffset(@Headers MessageHeaders messageHeaders) {
        Optional<ApiRequestMessage> originReq = headerHandler.getOriginRequest(messageHeaders);
        if (originReq.isPresent()) {
            String[] urlVariables = originReq.get().getUrlVariables();
            if (urlVariables != null && urlVariables.length == 2) {
                return Optional.of(new Offset(urlVariables[0], Integer.valueOf(urlVariables[1])));
            }
        }
        return Optional.empty();
    }

    /**
     * Get the list of comments and set the parentId for each comment
     *
     * @param zhihuComments
     * @return
     */
    private List<ZhihuComment> prepareZhihuComments(ZhihuComments zhihuComments, String parentId) {
        if (zhihuComments == null) {
            return Lists.newArrayList();
        }
        List<ZhihuComment> commentList = zhihuComments.getData();
        if (commentList != null) {
            String finalParentId = parentId;
            commentList.forEach(comment -> comment.setParentId(finalParentId));
        } else {
            commentList = Lists.newArrayList();
        }
        return commentList;
    }

    @Getter
    @AllArgsConstructor
    private static class Offset {
        private String parentId;
        private int offset;
    }

}
