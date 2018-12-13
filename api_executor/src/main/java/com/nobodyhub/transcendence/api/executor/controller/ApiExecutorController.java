package com.nobodyhub.transcendence.api.executor.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ApiExecutorController {
    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    void doRequest(@RequestBody ApiRequestMessage request) {
        //TODO execute in thread pool and return immdidately
        //TODO request the URL in request.getUrl()
        //TODO forward the response by request.getDestId()
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
         * qualifier for the response handler
         */
        private String destId;
    }
}
