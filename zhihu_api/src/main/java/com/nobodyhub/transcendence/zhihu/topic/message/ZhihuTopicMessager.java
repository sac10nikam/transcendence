package com.nobodyhub.transcendence.zhihu.topic.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.api.common.converter.ApiResponseConverter;
import com.nobodyhub.transcendence.api.common.executor.ApiAsyncExecutor;
import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.client.TopicHubClient;
import com.nobodyhub.transcendence.zhihu.common.cookies.ZhihuApiCookies;
import com.nobodyhub.transcendence.zhihu.configuration.ZhihuApiProperties;
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
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.nobodyhub.transcendence.api.common.kafka.KafkaMessageHeader.ORIGIN_REQUEST;
import static com.nobodyhub.transcendence.zhihu.topic.message.ZhihuApiChannel.*;

@Slf4j
@Component
@EnableBinding(ZhihuApiChannel.class)
public class ZhihuTopicMessager {
    private final ZhihuApiChannel channel;
    private final ApiResponseConverter converter;
    private final ObjectMapper objectMapper;
    private final ZhihuApiProperties apiProperties;
    private final ApiAsyncExecutor apiAsyncExecutor;
    private final ZhihuApiCookies cookies;
    private final KafkaHeaderHandler headerHandler;
    private final TopicHubClient topicHubClient;


    public ZhihuTopicMessager(ZhihuApiChannel channel,
                              ApiResponseConverter converter,
                              ObjectMapper objectMapper,
                              ZhihuApiProperties apiProperties,
                              ApiAsyncExecutor apiAsyncExecutor,
                              ZhihuApiCookies cookies,
                              KafkaHeaderHandler headerHandler,
                              TopicHubClient topicHubClient) {
        this.channel = channel;
        this.converter = converter;
        this.objectMapper = objectMapper;
        this.apiProperties = apiProperties;
        this.apiAsyncExecutor = apiAsyncExecutor;
        this.cookies = cookies;
        this.headerHandler = headerHandler;
        this.topicHubClient = topicHubClient;
    }

    /**
     * Retrieve message from queue of outbound request
     *
     * @param message contents of request
     * @throws InterruptedException
     */
    @StreamListener(ZHIHU_TOPIC_REQUEST_CHANNEL)
    public void receiveTopicRequest(ApiRequestMessage message) throws InterruptedException {
        apiAsyncExecutor.execRequest(message);
        // append the latest cookies
        cookies.inject(message);
        try {
            Thread.sleep(apiProperties.getDelay());
        } catch (InterruptedException e) {
            log.warn("Sleep interrupted by {}.", e);
            throw e;
        }
    }

    /**
     * Send request to get topics page(HTML)
     */
    public void getTopicCategories() {
        String url = "https://www.zhihu.com/topics";
        ApiRequestMessage message = new ApiRequestMessage(ZhihuApiChannel.IN_ZHIHU_TOPIC_CALLBACK_TOPIC_PAGE, url);
        channel.sendTopicRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_TOPIC_CALLBACK_TOPIC_PAGE)
    public void receiveTopicHtmlPage(@Payload byte[] message,
                                     @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
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
        ApiRequestMessage message = new ApiRequestMessage(ZhihuApiChannel.IN_ZHIHU_TOPIC_CALLBACK_PLAZZA_LIST, url);
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
        this.cookies.update(messageHeaders);
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
                Optional<ApiRequestMessage> originMsg = headerHandler.get(messageHeaders, ORIGIN_REQUEST);
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
        this.cookies.update(messageHeaders);
        Optional<ZhihuTopic> topic = converter.convert(message, ZhihuTopic.class);
        topic.ifPresent(this.topicHubClient::saveZhihuTopic);
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
        this.cookies.update(messageHeaders);
        Optional<ZhihuTopicList> topicList = converter.convert(message, ZhihuTopicList.class);
        if (topicList.isPresent()) {
            ZhihuTopicList list = topicList.get();
            if (!list.getData().isEmpty()) {
                for (ZhihuTopic topic : list.getData()) {
                    this.topicHubClient.saveZhihuTopic(topic);
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
        String url = "https://www.zhihu.com/api/v4/topics/{topicId}/feeds/essence?include=data[?(target.type=topic_sticky_module)].target.data[?(target.type=answer)].target.content,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp;data[?(target.type=topic_sticky_module)].target.data[?(target.type=answer)].target.is_normal,comment_count,voteup_count,content,relevant_info,excerpt.author.badge[?(type=best_answerer)].topics;data[?(target.type=topic_sticky_module)].target.data[?(target.type=article)].target.content,voteup_count,comment_count,voting,author.badge[?(type=best_answerer)].topics;data[?(target.type=topic_sticky_module)].target.data[?(target.type=people)].target.answer_count,articles_count,gender,follower_count,is_followed,is_following,badge[?(type=best_answerer)].topics;data[?(target.type=answer)].target.annotation_detail,content,hermes_label,is_labeled,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp;data[?(target.type=answer)].target.author.badge[?(type=best_answerer)].topics;data[?(target.type=article)].target.annotation_detail,content,hermes_label,is_labeled,author.badge[?(type=best_answerer)].topics;data[?(target.type=question)].target.annotation_detail,comment_count;&limit=10";
        ApiRequestMessage message = new ApiRequestMessage(IN_ZHIHU_TOPIC_CALLBACK_FEED_LIST, url, topicId);
        channel.sendTopicRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_TOPIC_CALLBACK_FEED_LIST)
    public void receiveTopicFeeds(@Payload byte[] message,
                                  @Headers MessageHeaders messageHeaders) {
        this.cookies.update(messageHeaders);
        Optional<ZhihuTopicFeedList> feedList = converter.convert(message, ZhihuTopicFeedList.class);
        if (feedList.isPresent()) {
            ZhihuTopicFeedList list = feedList.get();
            if (!list.getData().isEmpty()) {
                for (ZhihuTopicFeed feed : list.getData()) {
                    //TODO: save answer
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
