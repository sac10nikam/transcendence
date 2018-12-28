package com.nobodyhub.transcendence.api.executor.controller;

import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.api.executor.service.ApiExecutorService;
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
        apiExecutorService.fetchAndDispatch(request);
    }
}
