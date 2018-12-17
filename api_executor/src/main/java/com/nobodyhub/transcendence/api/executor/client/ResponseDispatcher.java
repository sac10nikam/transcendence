package com.nobodyhub.transcendence.api.executor.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
public class ResponseDispatcher {
    private final BinderAwareChannelResolver resolver;

    public ResponseDispatcher(BinderAwareChannelResolver resolver) {
        this.resolver = resolver;
    }

    public void dispatch(byte[] message, String topic, MediaType mediaType) {
        log.info("Execute dispatch asynchronously to {}!", topic);
        this.resolver.resolveDestination(topic).send(
            MessageBuilder.createMessage(
                message,
                new MessageHeaders(
                    Collections.singletonMap(MessageHeaders.CONTENT_TYPE, mediaType)
                )
            )
        );
    }
}
