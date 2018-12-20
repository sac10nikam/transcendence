package com.nobodyhub.transcendence.api.executor.service;


import com.google.common.base.Joiner;
import com.nobodyhub.transcendence.api.executor.client.ResponseDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        ResponseEntity<byte[]> entity = this.restTemplate.getForEntity(url, byte[].class);
        byte[] body = entity.getBody();
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
        // write message as byte[] into the queue
        responseDispatcher.dispatch(body, topic, mediaType);
    }
}
