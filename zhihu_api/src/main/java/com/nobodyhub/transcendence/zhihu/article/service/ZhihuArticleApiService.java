package com.nobodyhub.transcendence.zhihu.article.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.article.domain.ZhihuColumnArticles;
import com.nobodyhub.transcendence.zhihu.common.client.DeedHubClient;
import com.nobodyhub.transcendence.zhihu.common.cookies.ZhihuApiCookies;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuApiPaging;
import com.nobodyhub.transcendence.zhihu.common.domain.ZhihuComments;
import com.nobodyhub.transcendence.zhihu.common.service.ZhihuApiChannelBaseService;
import com.nobodyhub.transcendence.zhihu.configuration.ZhihuApiProperties;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuArticle;
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

import static com.nobodyhub.transcendence.zhihu.article.service.ZhihuArticleApiChannel.*;

@Slf4j
@Service
@EnableBinding(ZhihuArticleApiChannel.class)
public class ZhihuArticleApiService extends ZhihuApiChannelBaseService<ZhihuArticleApiChannel> {
    private final DeedHubClient deedHubClient;

    protected ZhihuArticleApiService(ZhihuArticleApiChannel channel,
                                     ApiResponseConverter converter,
                                     ApiAsyncExecutor apiAsyncExecutor,
                                     KafkaHeaderHandler headerHandler,
                                     @Qualifier(ZHIHU_ARTICLE_REQUEST_CHANNEL) PollableMessageSource requestMessageSource,
                                     ObjectMapper objectMapper,
                                     ZhihuApiProperties apiProperties,
                                     ZhihuApiCookies cookies,
                                     DeedHubClient deedHubClient) {
        super(channel, converter, apiAsyncExecutor, headerHandler, requestMessageSource, objectMapper, apiProperties, cookies);
        this.deedHubClient = deedHubClient;
    }

    /**
     * Get article of given id
     *
     * @param articleId
     */
    public void getById(String articleId) {
        String urlTemplate = "https://www.zhihu.com/api/v4/articles/{articleId}";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_ARTICLE_CALLBACK_ARTICLE, urlTemplate, articleId);
        channel.sendRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_ARTICLE_CALLBACK_ARTICLE)
    public void receiveArticle(@Payload byte[] message,
                               @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
        Optional<ZhihuArticle> article = converter.convert(message, ZhihuArticle.class);
        article.ifPresent(deedHubClient::saveZhihuArticleNoReturn);
    }

    /**
     * Get article of column with given id
     *
     * @param columnId
     */
    public void getByColumnId(String columnId) {
        String urlTemplate = "https://zhuanlan.zhihu.com/api2/columns/{columnId}/articles?include=data%5B*%5D.admin_closed_comment%2Ccomment_count%2Csuggest_edit%2Cis_title_image_full_screen%2Ccan_comment%2Cupvoted_followees%2Ccan_open_tipjar%2Ccan_tip%2Cvoteup_count%2Cvoting%2Ctopics%2Creview_info%2Cauthor.is_following%2Cis_labeled%2Clabel_info";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_ARTICLE_CALLBACK_COLUMN_ARTICLE, urlTemplate, columnId);
        channel.sendRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_ARTICLE_CALLBACK_COLUMN_ARTICLE)
    public void receiveArticleOfColumn(@Payload byte[] message,
                                       @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
        Optional<ZhihuColumnArticles> columnArticles = converter.convert(message, ZhihuColumnArticles.class);
        if (columnArticles.isPresent()) {
            List<ZhihuArticle> articleList = columnArticles.get().getData();
            if (articleList != null && !articleList.isEmpty()) {
                for (ZhihuArticle article : articleList) {
                    deedHubClient.saveZhihuArticleNoReturn(article);
                }
                // follow paging to ge more comments
                Optional<ApiRequestMessage> nextMsg = getNextUrl(columnArticles.get().getPaging(), IN_ZHIHU_ARTICLE_CALLBACK_COLUMN_ARTICLE);
                nextMsg.ifPresent((msg) -> channel.sendRequest().send(MessageBuilder.withPayload(msg).build()));
            }
        }
    }

    public void getCommentsByArticle(String articleId) {
        String urlTemplate = "https://www.zhihu.com/api/v4/articles/{articleId}/root_comments?include=data%5B*%5D.author%2Ccollapsed%2Creply_to_author%2Cdisliked%2Ccontent%2Cvoting%2Cvote_count%2Cis_parent_author%2Cis_author&order=normal&limit=20&offset=0&status=open";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_ARTICLE_CALLBACK_COMMENT, urlTemplate, articleId);
        channel.sendRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_ARTICLE_CALLBACK_COMMENT)
    public void receiveArticleComments(@Payload byte[] message,
                                       @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
        Optional<ZhihuComments> comments = converter.convert(message, ZhihuComments.class);
        if (comments.isPresent()) {
            List<ZhihuComment> commentList = prepareZhihuComments(comments.get(), messageHeaders);
            if (commentList != null && !commentList.isEmpty()) {
                for (ZhihuComment comment : commentList) {
                    deedHubClient.saveZhihuCommentNoReturn(comment);
                }
                // follow paging to ge more comments
                ZhihuApiPaging paging = comments.get().getPaging();
                if (paging != null
                    && paging.getNext() != null
                    && !paging.getIsEnd()) {
                    ApiRequestMessage next = new ApiRequestMessage(IN_ZHIHU_ARTICLE_CALLBACK_COMMENT, paging.getNext());
                    channel.sendRequest().send(MessageBuilder.withPayload(next).build());
                }
            }
        }
    }
}
