package com.nobodyhub.transcendence.api.common;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "api-executor")
public interface ApiServiceClient {
    @PostMapping
    void doRequest(@RequestBody ApiRequestMessage request);
}
