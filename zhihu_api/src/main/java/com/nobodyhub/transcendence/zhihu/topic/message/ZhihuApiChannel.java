package com.nobodyhub.transcendence.zhihu.topic.message;

import com.nobodyhub.transcendence.zhihu.topic.domain.ZhihuTopic;
import com.nobodyhub.transcendence.zhihu.topic.domain.feed.ZhihuTopicFeedList;
import com.nobodyhub.transcendence.zhihu.topic.domain.paging.ZhihuTopicList;
import com.nobodyhub.transcendence.zhihu.topic.domain.plazza.ZhihuTopicPlazzaList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ZhihuApiChannel {
    /**
     * Request sent to fetch Topic information
     */
    String OUT_ZHIHU_TOPIC_REQUEST = "out-zhihu-topic-request";

    @Output(OUT_ZHIHU_TOPIC_REQUEST)
    MessageChannel sendTopicRequest();

    String ZHIHU_TOPIC_REQUEST_CHANNEL = "zhihu-topic-request-channel";

    @Qualifier(ZHIHU_TOPIC_REQUEST_CHANNEL)
    @Input(OUT_ZHIHU_TOPIC_REQUEST)
    PollableMessageSource receiveTopicRequest();

    /**
     * Response received contains <b>HTML</b> page for topics
     *
     * @see ZhihuTopicMessager#getTopicCategories()
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
