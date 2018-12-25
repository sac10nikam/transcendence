package com.nobodyhub.transcendence.api.executor.controller;

import com.nobodyhub.transcendence.api.executor.service.ApiExecutorService;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ApiExecutorController {

    private final ApiExecutorService apiExecutorService;

    public ApiExecutorController(ApiExecutorService apiExecutorService) {
        this.apiExecutorService = apiExecutorService;
    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    void doRequest(@RequestBody ApiRequestMessage request) {
        apiExecutorService.fetchAndDispatch(request);
    }

    @ToString
    @Data
    public class ApiRequestMessage {
        /**
         * Request Method
         */
        private HttpMethod method = HttpMethod.GET;

        /**
         * Request headers
         */
        private MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        /**
         * Request URL
         */
        private String url;

        /**
         * Request body
         */
        private String body;

        /**
         * topic to cache the response
         */
        private String topic;

        /**
         * Used by Json serializer
         */
        public ApiRequestMessage() {
        }

        public ApiRequestMessage(String url, String topic) {
            this.url = url;
            this.topic = topic;
        }
    }
}
