package com.nobodyhub.transcendence.api.executor.service;


import com.nobodyhub.transcendence.api.common.kafka.KafkaHeaderHandler;
import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.api.executor.cookies.ApiCookies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URI;

import static com.nobodyhub.transcendence.api.common.kafka.KafkaMessageHeader.ORIGIN_REQUEST;
import static com.nobodyhub.transcendence.api.common.kafka.KafkaMessageHeader.RESPONSE_HEADERS;


@Slf4j
@Service
public class ApiExecutorService {
    private final RestTemplate restTemplate;
    private final BinderAwareChannelResolver resolver;
    private final KafkaHeaderHandler headerHandler;
    private final ApiCookies cookies;

    public ApiExecutorService(RestTemplate restTemplate,
                              BinderAwareChannelResolver resolver,
                              KafkaHeaderHandler headerHandler,
                              ApiCookies cookies) {
        this.restTemplate = restTemplate;
        this.resolver = resolver;
        this.headerHandler = headerHandler;
        this.cookies = cookies;
    }

    @Async
    public void fetchAndDispatch(ApiRequestMessage requestMessage) {
        // make http request
        cookies.inject(requestMessage);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(requestMessage.getBody(), requestMessage.getHeaders());
        byte[] respBody = null;
        HttpHeaders respHeaders = null;
        URI url = restTemplate.getUriTemplateHandler().expand(requestMessage.getUrlTemplate(), (Object[]) requestMessage.getUrlVariables());
        try {
            log.info("Proceed to {} {}.", requestMessage.getMethod(), url.toString());
            ResponseEntity<byte[]> responseEntity = this.restTemplate.exchange(
                url,
                requestMessage.getMethod(),
                entity,
                byte[].class);
            respBody = responseEntity.getBody();
            respHeaders = responseEntity.getHeaders();
            this.cookies.extract(respHeaders, requestMessage.getTopic());
        } catch (RestClientException e) {
            log.error("Fail to process request message {}!", requestMessage);
            log.error("with error", e);
        }
        if (respBody == null) {
            log.info("Received Empty Response Body. Skipped!");
            return;
        }
        // serialize the response body
        byte[] message = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(respBody);
            message = bos.toByteArray();
        } catch (IOException e) {
            log.error("Error happends when serializing response to byte[].", e);
            return;
        }
        // write message(with header) as byte[] into the queue
        log.info("Sending message to topic [{}]", requestMessage.getTopic());
        this.resolver.resolveDestination(requestMessage.getTopic()).send(
            MessageBuilder.createMessage(
                message,
                headerHandler.builder()
                    .put(RESPONSE_HEADERS.getKey(), respHeaders)
                    .put(ORIGIN_REQUEST.getKey(), requestMessage)
                    .build()
            )
        );
    }
}
