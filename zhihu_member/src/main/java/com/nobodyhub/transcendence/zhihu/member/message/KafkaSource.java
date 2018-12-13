package com.nobodyhub.transcendence.zhihu.member.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaSource {
    private final Source source;

    public KafkaSource(Source source) {
        this.source = source;
    }

    public void requestZhihuMemberApi(String urlToken) {
        log.info("Sending Kafka message for urlToken: {}.", urlToken);
        ApiRequestMessage message = new ApiRequestMessage("member", urlToken);
        this.source.output().send(MessageBuilder.withPayload(message).build());
    }
}
