package com.nobodyhub.transcendence.zhihu.topic.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobodyhub.transcendence.zhihu.common.converter.ZhihuUrlConverter;
import com.nobodyhub.transcendence.zhihu.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.common.message.ApiResponseConverter;
import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopic;
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
import org.springframework.http.MediaType;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.nobodyhub.transcendence.zhihu.topic.message.ZhihuApiChannel.*;

@Slf4j
@Component
@EnableBinding(ZhihuApiChannel.class)
public class ZhihuTopicMessager {
    private final ZhihuApiChannel channel;
    private final ApiResponseConverter converter;
    private final ZhihuUrlConverter urlConverter;
    private final ObjectMapper objectMapper;


    public ZhihuTopicMessager(ZhihuApiChannel channel,
                              ApiResponseConverter converter,
                              ZhihuUrlConverter urlConverter,
                              ObjectMapper objectMapper) {
        this.channel = channel;
        this.converter = converter;
        this.urlConverter = urlConverter;
        this.objectMapper = objectMapper;
    }

    /**
     * Send request to get topics page(HTML)
     */
    public void getTopicCategories() {
        String url = "https://www.zhihu.com/topics";
        ApiRequestMessage message = new ApiRequestMessage(url, ZhihuApiChannel.IN_ZHIHU_TOPIC_CALLBACK_TOPIC_PAGE);
        channel.sendTopicRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(ZhihuApiChannel.IN_ZHIHU_TOPIC_CALLBACK_TOPIC_PAGE)
    public void receiveTopicHtmlPage(@Payload byte[] message) {
        Optional<String> html = converter.convert(message, String.class);
        if (html.isPresent()) {
            Document doc = Jsoup.parse(html.get());
            Elements categories = doc.select(".zm-topic-cat-item");
            for (Element c : categories) {
                ZhihuTopicCategory topicCategory = new ZhihuTopicCategory(Integer.valueOf(c.attr("data-id")), c.text());
                //TODO: Store topic Category
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
        // header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // form data
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
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
        ApiRequestMessage message = new ApiRequestMessage(url, ZhihuApiChannel.IN_ZHIHU_TOPIC_CALLBACK_PLAZZA_LIST);
        message.setHeaders(headers);
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

    @StreamListener(ZhihuApiChannel.IN_ZHIHU_TOPIC_CALLBACK_PLAZZA_LIST)
    public void receiveTopicPLazzaList(@Payload byte[] message, @Headers Map<String, Object> headers) {
        Optional<ZhihuTopicPlazzaList> plazzaList = converter.convert(message, ZhihuTopicPlazzaList.class);
        if (plazzaList.isPresent()) {
            List<String> htmls = plazzaList.get().getMsg();
            for (String html : htmls) {
                Elements links = Jsoup.parse(html).select(".blk a:not(.zg-follow)");
                for (Element link : links) {
                    String topicId = link.attr("href").replace("/topic/", "");
                    // request for topic detail
                    getTopicById(topicId);
                    // request for topic feeds(answer)
                    getTopicFeeds(topicId);
                }
            }
            if (!htmls.isEmpty()) {
                ApiRequestMessage originMsg = (ApiRequestMessage) headers.get("origin-request");
                List<String> params = originMsg.getHeaders().get("params");
                if (params != null && params.size() == 1) {
                    try {
                        TopicsPlazzaListParam param = this.objectMapper.readValue(params.get(0), TopicsPlazzaListParam.class);
                        getTopicIdsByCategory(param.getTopicId(), param.getOffset() + htmls.size());
                    } catch (IOException e) {
                        log.error("Fail to eserialize [{}] with error [{}]", params.get(0), e);
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
        final String topicUrl = this.urlConverter.expand(
            "/api/v4/topics/{id}",
            topicId);
        channel.sendTopicRequest().send(MessageBuilder.withPayload(
            new ApiRequestMessage(topicUrl, ZhihuApiChannel.IN_ZHIHU_TOPIC_CALLBACK_TOPIC)
        ).build());
        // request for parent topic detail
        final String topicParentUrl = this.urlConverter.expand("/api/v4/topics/{id}/parent",
            topicId);
        channel.sendTopicRequest().send(MessageBuilder.withPayload(
            new ApiRequestMessage(topicParentUrl, ZhihuApiChannel.IN_ZHIHU_TOPIC_CALLBACK_TOPIC_LIST)
        ).build());
        // request for children topic detail
        final String topicChildUrl = this.urlConverter.expand("/api/v4/topics/{id}/children",
            topicId);
        channel.sendTopicRequest().send(MessageBuilder.withPayload(
            new ApiRequestMessage(topicChildUrl, ZhihuApiChannel.IN_ZHIHU_TOPIC_CALLBACK_TOPIC_LIST)
        ).build());
    }

    @StreamListener(IN_ZHIHU_TOPIC_CALLBACK_TOPIC)
    public void receiveTopic(@Payload byte[] message) {
        Optional<ZhihuTopic> topic = converter.convert(message, ZhihuTopic.class);
        if (topic.isPresent()) {
            //TODO: save topic
        }
    }

    @StreamListener(IN_ZHIHU_TOPIC_CALLBACK_TOPIC_LIST)
    public void receiveTopicList(@Payload byte[] message) {
        Optional<ZhihuTopicList> topicList = converter.convert(message, ZhihuTopicList.class);
        if (topicList.isPresent()) {
            ZhihuTopicList list = topicList.get();
            if (!list.getData().isEmpty()) {
                for (ZhihuTopic topic : list.getData()) {
                    //TODO: save topic
                }
                // request for more data
                if (!list.getPaging().getIsEnd()) {
                    channel.sendTopicRequest().send(MessageBuilder.withPayload(
                        new ApiRequestMessage(list.getPaging().getNext(), ZhihuApiChannel.IN_ZHIHU_TOPIC_CALLBACK_TOPIC_LIST)
                    ).build());
                }
            }
        }
    }

    public void getTopicFeeds(String topicId) {
        String url = this.urlConverter.expand("/api/v4/topics/{topicId}/feeds/essence?include=data[?(target.type=topic_sticky_module)].target.data[?(target.type=answer)].target.content,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp;data[?(target.type=topic_sticky_module)].target.data[?(target.type=answer)].target.is_normal,comment_count,voteup_count,content,relevant_info,excerpt.author.badge[?(type=best_answerer)].topics;data[?(target.type=topic_sticky_module)].target.data[?(target.type=article)].target.content,voteup_count,comment_count,voting,author.badge[?(type=best_answerer)].topics;data[?(target.type=topic_sticky_module)].target.data[?(target.type=people)].target.answer_count,articles_count,gender,follower_count,is_followed,is_following,badge[?(type=best_answerer)].topics;data[?(target.type=answer)].target.annotation_detail,content,hermes_label,is_labeled,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp;data[?(target.type=answer)].target.author.badge[?(type=best_answerer)].topics;data[?(target.type=article)].target.annotation_detail,content,hermes_label,is_labeled,author.badge[?(type=best_answerer)].topics;data[?(target.type=question)].target.annotation_detail,comment_count;&limit=10",
            topicId);
        ApiRequestMessage message = new ApiRequestMessage(url, IN_ZHIHU_TOPIC_CALLBACK_FEED_LIST);
        channel.sendTopicRequest().send(MessageBuilder.withPayload(message).build());
    }

    @StreamListener(IN_ZHIHU_TOPIC_CALLBACK_FEED_LIST)
    public void receiveTopicFeeds(@Payload byte[] message) {
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
                        new ApiRequestMessage(list.getPaging().getNext(), IN_ZHIHU_TOPIC_CALLBACK_FEED_LIST)
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
    @ServiceActivator(inputChannel = ZhihuApiChannel.OUT_ZHIHU_TOPIC_REQUEST + ".zhihu-topic.errors")
    public void error(Message<?> message) {

    }
}
