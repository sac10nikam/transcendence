package com.nobodyhub.transcendence.zhihu.article.service;

import com.nobodyhub.transcendence.api.common.message.ApiChannel;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ZhihuArticleApiChannel extends ApiChannel {
    /**
     * Request sent to fetch Zhihu Article information
     */
    String OUT_ZHIHU_ARTICLE_REQUEST = "out-zhihu-article-request";

    @Output(OUT_ZHIHU_ARTICLE_REQUEST)
    MessageChannel sendRequest();

    String ZHIHU_ARTICLE_REQUEST_CHANNEL = "zhihu-article-request-channel";

    @Input(ZHIHU_ARTICLE_REQUEST_CHANNEL)
    PollableMessageSource receiveRequest();

    /**
     * Response received contains article basics via API
     */
    String IN_ZHIHU_ARTICLE_CALLBACK_ARTICLE = "in-zhihu-article-callback-article";

    @Input(IN_ZHIHU_ARTICLE_CALLBACK_ARTICLE)
    SubscribableChannel articleCallback();

    /**
     * Response received contains article contents via html
     */
    String IN_ZHIHU_ARTICLE_CALLBACK_ARTICLE_CONTENT = "in-zhihu-article-callback-article-content";

    @Input(IN_ZHIHU_ARTICLE_CALLBACK_ARTICLE_CONTENT)
    SubscribableChannel articleContentCallback();

    /**
     * Response received for article of specific column
     */
    String IN_ZHIHU_ARTICLE_CALLBACK_COLUMN_ARTICLE = "in-zhihu-article-callback-column-article";

    @Input(IN_ZHIHU_ARTICLE_CALLBACK_COLUMN_ARTICLE)
    SubscribableChannel columnArticleCallback();
}
