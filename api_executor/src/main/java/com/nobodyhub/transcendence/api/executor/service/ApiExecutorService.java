package com.nobodyhub.transcendence.api.executor.service;


import com.google.common.base.Joiner;
import com.nobodyhub.transcendence.api.executor.client.ResponseDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.IMAGE_JPEG;


@Slf4j
@Service
public class ApiExecutorService {
    private final RestTemplate restTemplate;
    private final ResponseDispatcher responseDispatcher;

    public ApiExecutorService(RestTemplate restTemplate, ResponseDispatcher responseDispatcher) {
        this.restTemplate = restTemplate;
        this.responseDispatcher = responseDispatcher;
    }

    @Async
    public void fetchAndDispatch(String topic, String url) {
        ResponseEntity<Object> entity = this.restTemplate.getForEntity(url, Object.class);
        Object body = entity.getBody();
        if (body == null) {
            log.info("Received Empty Response Body. Skipped!");
            return;
        }
        MediaType mediaType = entity.getHeaders().getContentType();
        if (mediaType == null
            || (!mediaType.includes(APPLICATION_JSON)
            && !mediaType.includes(IMAGE_JPEG))) {
            //TODO: add more content types
            log.info("The Response contents should be one of following types: {}",
                Joiner.on(",").join(APPLICATION_JSON, IMAGE_JPEG));
        }
        // serialize object to byte[] message and send
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            //https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(body);
            byte[] message = bos.toByteArray();
            responseDispatcher.dispatch(message, topic, mediaType);
        } catch (IOException e) {
            log.error("Error happends when convert Object to byte[].", e);
        }
    }
}
