package com.nobodyhub.transcendence.zhihu.topic.message;

import com.nobodyhub.transcendence.zhihu.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.zhihu.configuration.ZhihuApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;

import static com.nobodyhub.transcendence.zhihu.topic.message.ZhihuApiChannel.ZHIHU_TOPIC_REQUEST_CHANNEL;

@Slf4j
@Configuration
public class ZhihuApiConfiguration {
    private final ZhihuApiProperties apiProperties;
    private final ApiServiceClient apiServiceClient;

    public ZhihuApiConfiguration(ZhihuApiProperties apiProperties,
                                 ApiServiceClient apiServiceClient) {
        this.apiProperties = apiProperties;
        this.apiServiceClient = apiServiceClient;
    }

    @Bean
    public ApplicationRunner poller(
        @Qualifier(ZHIHU_TOPIC_REQUEST_CHANNEL) PollableMessageSource topicRequestSrc) {
        return args -> {
            while (true) {
                final ApiRequestMessage[] message = new ApiRequestMessage[1];
                try {
                    if (!topicRequestSrc.poll(m -> {
                        message[0] = (ApiRequestMessage) m.getPayload();
                        apiServiceClient.doRequest(message[0]);
                    }, new ParameterizedTypeReference<ApiRequestMessage>() {
                    })) {
                        Thread.sleep(apiProperties.getDelay());
                    }
                } catch (Exception e) {
                    // handle failure (throw an exception to reject the message)
                    log.error("Fail to make request for Message[{}] due to error[{}]!", message[0], e);
                }
            }

        };
    }
}
