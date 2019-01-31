package com.nobodyhub.transcendence.api.common.executor;

import com.nobodyhub.transcendence.message.ApiRequestMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ApiAsyncExecutor {

    private final ApiServiceClient apiServiceClient;

    public ApiAsyncExecutor(ApiServiceClient apiServiceClient) {
        this.apiServiceClient = apiServiceClient;
    }

    @Async
    public void execRequest(ApiRequestMessage message) {
        apiServiceClient.doRequest(message);
    }
}
