package com.nobodyhub.transcendence.api.executor.service;


import com.google.common.collect.Maps;
import com.nobodyhub.transcendence.api.executor.controller.ApiExecutorController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Map;


@Slf4j
@Service
public class ApiExecutorService {
    private final RestTemplate restTemplate;
    private final BinderAwareChannelResolver resolver;

    public ApiExecutorService(RestTemplate restTemplate,
                              BinderAwareChannelResolver resolver) {
        this.restTemplate = restTemplate;
        this.resolver = resolver;
    }

    @Async
    public void fetchAndDispatch(ApiExecutorController.ApiRequestMessage requestMessage) {
        // make http request
        HttpEntity<String> entity = new HttpEntity<>(requestMessage.getBody(), requestMessage.getHeaders());
        ResponseEntity<byte[]> responseEntity = this.restTemplate.exchange(
            requestMessage.getUrl(),
            requestMessage.getMethod(),
            entity,
            byte[].class);
        byte[] body = responseEntity.getBody();
        if (body == null) {
            log.info("Received Empty Response Body. Skipped!");
            return;
        }
        // serialize the response body
        byte[] message = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(body);
            message = bos.toByteArray();
        } catch (IOException e) {
            log.error("Error happends when serializing response to byte[].", e);
            return;
        }
        // prepare message header
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("reponse-headers", responseEntity.getHeaders());
        headers.put("origin-request", requestMessage);
        // write message as byte[] into the queue
        log.info("Sending message to topic [{}]", requestMessage.getTopic());
        this.resolver.resolveDestination(requestMessage.getTopic()).send(
            MessageBuilder.createMessage(
                message,
                new MessageHeaders(headers)
            )
        );
    }
}
