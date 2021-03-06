package com.nobodyhub.transcendence.zhihu.topic.service;


import com.nobodyhub.transcendence.api.common.message.ApiChannel;
import com.nobodyhub.transcendence.zhihu.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.topic.domain.feed.ZhihuTopicFeedList;
import com.nobodyhub.transcendence.zhihu.topic.domain.paging.ZhihuTopicList;
import com.nobodyhub.transcendence.zhihu.topic.domain.plazza.ZhihuTopicPlazzaList;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ZhihuTopicApiChannel extends ApiChannel {
    /**
     * Request sent as admin
     */
    String OUT_ADMIN_PRIORITY_REQUEST_CHANNEL = "admin-priority-request-channel";

    @Output(OUT_ADMIN_PRIORITY_REQUEST_CHANNEL)
    MessageChannel sendAdminRequest();

    /**
     * Request sent to fetch Topic information
     */
    String OUT_ZHIHU_TOPIC_REQUEST = "out-zhihu-topic-request";

    @Output(OUT_ZHIHU_TOPIC_REQUEST)
    MessageChannel sendTopicRequest();

    /**
     * Response received contains <b>HTML</b> page for topics
     *
     * @see ZhihuTopicApiService#getTopicCategories()
     */
    String IN_ZHIHU_TOPIC_CALLBACK_TOPIC_PAGE = "in-zhihu-topic-callback-topic-page";

    @Input(IN_ZHIHU_TOPIC_CALLBACK_TOPIC_PAGE)
    SubscribableChannel topicCallBackTopicPage();

    /**
     * Response received contains Topic information
     *
     * @see ZhihuTopic
     */
    String IN_ZHIHU_TOPIC_CALLBACK_TOPIC = "in-zhihu-topic-callback-topic";

    @Input(IN_ZHIHU_TOPIC_CALLBACK_TOPIC)
    SubscribableChannel topicCallBackTopic();

    /**
     * Response received contains information for a list of Topic
     *
     * @see ZhihuTopicList
     */
    String IN_ZHIHU_TOPIC_CALLBACK_TOPIC_LIST = "in-zhihu-topic-callback-topic-list";

    @Input(IN_ZHIHU_TOPIC_CALLBACK_TOPIC_LIST)
    SubscribableChannel topicCallBackTopicList();

    /**
     * Response received contains information for a list of Topic ids
     *
     * @see ZhihuTopicPlazzaList
     */
    String IN_ZHIHU_TOPIC_CALLBACK_PLAZZA_LIST = "in-zhihu-topic-callback-plazza-list";

    @Input(IN_ZHIHU_TOPIC_CALLBACK_PLAZZA_LIST)
    SubscribableChannel topicCallBackPlazzaList();

    /**
     * Response received contains information for feeds(answers) of Topic
     *
     * @see ZhihuTopicFeedList
     */
    String IN_ZHIHU_TOPIC_CALLBACK_FEED_LIST = "in-zhihu-topic-callback-feed-list";

    @Input(IN_ZHIHU_TOPIC_CALLBACK_FEED_LIST)
    SubscribableChannel topicCallBackFeeList();


}
