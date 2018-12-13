package com.nobodyhub.transcendence.api.throttle.client;

import com.nobodyhub.transcendence.api.throttle.message.ApiRequestMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "api_service")
public interface ApiServiceClient {
    @PostMapping
    void doRequest(@RequestBody ApiRequestMessage request);
}
