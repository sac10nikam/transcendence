package com.nobodyhub.transcendence.api.executor.controller;

import com.nobodyhub.transcendence.api.executor.service.ApiExecutorService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;
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
        apiExecutorService.fetchAndDispatch(request.getTopic(), request.getUrl());
    }

    @Data
    @AllArgsConstructor
    @ToString
    static class ApiRequestMessage {
        /**
         * the url to request
         * TODO: add cookies support
         */
        private String url;
        /**
         * the topic to which the callback will be sent
         */
        private String topic;
    }
}
