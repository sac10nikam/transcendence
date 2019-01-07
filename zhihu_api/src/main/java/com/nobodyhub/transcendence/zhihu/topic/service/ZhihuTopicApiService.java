package com.nobodyhub.transcendence.zhihu.topic.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.cookies.ApiCookies;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.common.client.DeedHubClient;
import com.nobodyhub.transcendence.zhihu.common.client.TopicHubClient;
import com.nobodyhub.transcendence.zhihu.common.configuration.ZhihuApiProperties;
import com.nobodyhub.transcendence.zhihu.common.service.ZhihuApiChannelBaseService;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuAnswer;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuArticle;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuFeedContent;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopicCategory;
import com.nobodyhub.transcendence.zhihu.topic.domain.feed.ZhihuTopicFeed;
import com.nobodyhub.transcendence.zhihu.topic.domain.feed.ZhihuTopicFeedList;
import com.nobodyhub.transcendence.zhihu.topic.domain.paging.ZhihuTopicList;
import com.nobodyhub.transcendence.zhihu.topic.domain.plazza.ZhihuTopicPlazzaList;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.nobodyhub.transcendence.zhihu.topic.service.ZhihuTopicApiChannel.*;

@Slf4j
@Service
@EnableBinding(ZhihuTopicApiChannel.class)
public class ZhihuTopicApiService extends ZhihuApiChannelBaseService<ZhihuTopicApiChannel> {

    private final TopicHubClient topicHubClient;
    private final DeedHubClient deedHubClient;


    public ZhihuTopicApiService(ZhihuTopicApiChannel channel,
                                ApiResponseConverter converter,
                                ApiAsyncExecutor apiAsyncExecutor,
                                KafkaHeaderHandler headerHandler,
                                @Qualifier(ZHIHU_TOPIC_REQUEST_CHANNEL) PollableMessageSource requestMessageSource,
                                ObjectMapper objectMapper,
                                ZhihuApiProperties apiProperties,
                                ApiCookies cookies,
                                TopicHubClient topicHubClient,
                                DeedHubClient deedHubClient) {
        super(channel, converter, apiAsyncExecutor, headerHandler, requestMessageSource, objectMapper, apiProperties, cookies);
        this.topicHubClient = topicHubClient;
        this.deedHubClient = deedHubClient;
    }

    /**
     * Send request to get topics page(HTML)
     */
    public void getTopicCategories() {
        String url = "https://www.zhihu.com/topics";
        ApiRequestMessage message = new ApiRequestMessage(ZhihuTopicApiChannel.IN_ZHIHU_TOPIC_CALLBACK_TOPIC_PAGE, url);
        channel.sendTopicRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_TOPIC_CALLBACK_TOPIC_PAGE)
    public void receiveTopicHtmlPage(@Payload byte[] message,
                                     @Headers MessageHeaders messageHeaders) {
        this.cookies.extract(messageHeaders);
        Optional<String> html = converter.convert(message, String.class);
        if (html.isPresent()) {
            Document doc = Jsoup.parse(html.get());
            Elements categories = doc.select(".zm-topic-cat-item");
            for (Element c : categories) {
                ZhihuTopicCategory topicCategory = new ZhihuTopicCategory(Integer.valueOf(c.attr("data-id")), c.text());
                getTopicIdsByCategory(topicCategory.getDataId(), 0);
            }
        }
    }

    /**
     * Send request to get topic ids belong to given category
     *
     * @param dataId the data id to query the topics
     * @param offset offset of the result list
     */
    public void getTopicIdsByCategory(Integer dataId, int offset) {
        String url = "https://www.zhihu.com/node/TopicsPlazzaListV2";
        // form data
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("method", "next");
        TopicsPlazzaListParam params = new TopicsPlazzaListParam();
        params.setTopicId(dataId);
        params.setOffset(offset);
        try {
            body.add("params", objectMapper.writeValueAsString(params));
        } catch (JsonProcessingException e) {
            log.error("Fail to serialize TopicsPlazzaListParam[{}] with error[{}]", params, e);
            // use string interpolation instead
            body.add("params", String.format("{\"topic_id\":%d,\"offset\":%d,\"hash_id\":\"\"}",
                dataId, offset));
        }
        ApiRequestMessage message = new ApiRequestMessage(ZhihuTopicApiChannel.IN_ZHIHU_TOPIC_CALLBACK_PLAZZA_LIST, url);
        message.setMethod(HttpMethod.POST);
        message.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA.toString());
        message.setBody(body);
        channel.sendTopicRequest().send(MessageBuilder.withPayload(message).build());
    }

    @ToString
    @Data
    private static class TopicsPlazzaListParam {
        @JsonProperty("topic_id")
        private Integer topicId;
        @JsonProperty("offset")
        private Integer offset;
        @JsonProperty("hash_id")
        private Integer hashId;
    }

    @StreamListener(IN_ZHIHU_TOPIC_CALLBACK_PLAZZA_LIST)
    public void receiveTopicPlazzaList(@Payload byte[] message,
                                       @Headers MessageHeaders messageHeaders) {
        this.cookies.extract(messageHeaders);
        Optional<ZhihuTopicPlazzaList> plazzaList = converter.convert(message, ZhihuTopicPlazzaList.class);
        if (plazzaList.isPresent()) {
            List<String> htmls = plazzaList.get().getMsg();
            for (String html : htmls) {
                Elements links = Jsoup.parse(html).select(".blk a:not(.zg-follow)");
                for (Element link : links) {
                    String topicId = link.attr("href").replace("/topic/", "");
                    // request for topic detail
                    getTopicById(topicId);
                    getTopicParents(topicId);
                    getTopicChildrent(topicId);
                    // request for topic feeds(answer)
                    getTopicFeeds(topicId);
                }
            }
            if (!htmls.isEmpty()) {
                Optional<ApiRequestMessage> originMsg = headerHandler.getOriginRequest(messageHeaders);
                if (originMsg.isPresent()) {
                    List<Object> params = originMsg.get().getBody().get("params");
                    if (params != null && params.size() == 1) {
                        try {
                            TopicsPlazzaListParam param = this.objectMapper.readValue((String) params.get(0), TopicsPlazzaListParam.class);
                            getTopicIdsByCategory(param.getTopicId(), param.getOffset() + htmls.size());
                        } catch (IOException e) {
                            log.error("Fail to eserialize [{}] with error [{}]", params.get(0), e);
                        }
                    }
                }
            }
        }
    }

    /**
     * Send request to get topic detail
     *
     * @param topicId the id of request topic
     */
    public void getTopicById(String topicId) {
        // request for topic detail
        final String topicUrl = "https://www.zhihu.com/api/v4/topics/{id}";
        channel.sendTopicRequest().send(MessageBuilder.withPayload(
            new ApiRequestMessage(IN_ZHIHU_TOPIC_CALLBACK_TOPIC, topicUrl, topicId)
        ).build());
    }

    @StreamListener(IN_ZHIHU_TOPIC_CALLBACK_TOPIC)
    public void receiveTopic(@Payload byte[] message,
                             @Headers MessageHeaders messageHeaders) {
        this.cookies.extract(messageHeaders);
        Optional<ZhihuTopic> topic = converter.convert(message, ZhihuTopic.class);
        topic.ifPresent(this.topicHubClient::saveZhihuTopicNoReturn);
    }

    public void getTopicParents(String topicId) {
        final String topicParentUrl = "https://www.zhihu.com/api/v4/topics/{id}/parent";
        channel.sendTopicRequest().send(MessageBuilder.withPayload(
            new ApiRequestMessage(IN_ZHIHU_TOPIC_CALLBACK_TOPIC_LIST, topicParentUrl, topicId)
        ).build());
    }

    public void getTopicChildrent(String topicId) {
        final String topicChildUrl = "https://www.zhihu.com/api/v4/topics/{id}/children";
        channel.sendTopicRequest().send(MessageBuilder.withPayload(
            new ApiRequestMessage(IN_ZHIHU_TOPIC_CALLBACK_TOPIC_LIST, topicChildUrl, topicId)
        ).build());
    }

    @StreamListener(IN_ZHIHU_TOPIC_CALLBACK_TOPIC_LIST)
    public void receiveTopicList(@Payload byte[] message,
                                 @Headers MessageHeaders messageHeaders) {
        this.cookies.extract(messageHeaders);
        Optional<ZhihuTopicList> topicList = converter.convert(message, ZhihuTopicList.class);
        if (topicList.isPresent()) {
            ZhihuTopicList list = topicList.get();
            if (!list.getData().isEmpty()) {
                for (ZhihuTopic topic : list.getData()) {
                    this.topicHubClient.saveZhihuTopicNoReturn(topic);
                }
                // request for more data
                if (!list.getPaging().getIsEnd()) {
                    channel.sendTopicRequest().send(MessageBuilder.withPayload(
                        new ApiRequestMessage(IN_ZHIHU_TOPIC_CALLBACK_TOPIC_LIST, list.getPaging().getNext())
                    ).build());
                }
            }
        }
    }

    public void getTopicFeeds(String topicId) {
        String url = "http://www.zhihu.com/api/v4/topics/{topicId}/feeds/essence";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_TOPIC_CALLBACK_FEED_LIST, url, topicId);
        channel.sendTopicRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_TOPIC_CALLBACK_FEED_LIST)
    public void receiveTopicFeeds(@Payload byte[] message,
                                  @Headers MessageHeaders messageHeaders) {
        this.cookies.extract(messageHeaders);
        Optional<ZhihuTopicFeedList> feedList = converter.convert(message, ZhihuTopicFeedList.class);
        if (feedList.isPresent()) {
            ZhihuTopicFeedList list = feedList.get();
            if (!list.getData().isEmpty()) {
                for (ZhihuTopicFeed feed : list.getData()) {
                    ZhihuFeedContent content = feed.getTarget();
                    switch (content.getType()) {
                        case "answer": {
                            deedHubClient.saveZhihuAnswerNoReturn((ZhihuAnswer) content);
                            break;
                        }
                        case "article": {
                            deedHubClient.saveZhihuArticleNoReturn((ZhihuArticle) content);
                            break;
                        }
                        default: {
                            // do nothing
                            break;
                        }
                    }

                }
                // request for more data
                if (!list.getPaging().getIsEnd()) {
                    channel.sendTopicRequest().send(MessageBuilder.withPayload(
                        new ApiRequestMessage(IN_ZHIHU_TOPIC_CALLBACK_FEED_LIST, list.getPaging().getNext())
                    ).build());
                }
            }

        }
    }

    /**
     * TOOD: error handling
     *
     * @param message
     * @see <a href="https://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/">Error Handling</a>
     */
    @ServiceActivator(inputChannel = OUT_ZHIHU_TOPIC_REQUEST + ".zhihu-topic.errors")
    public void error(Message<?> message) {

    }
}
