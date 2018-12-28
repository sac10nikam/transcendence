package com.nobodyhub.transcendence.api.common.executor;


import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "api-executor")
public interface ApiServiceClient {
    @PostMapping
    void doRequest(@RequestBody ApiRequestMessage request);
}
