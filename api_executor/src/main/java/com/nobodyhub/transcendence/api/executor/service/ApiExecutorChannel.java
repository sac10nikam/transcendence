package com.nobodyhub.transcendence.api.executor.service;

import com.nobodyhub.transcendence.api.common.message.ApiChannel;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ApiExecutorChannel extends ApiChannel {

    /**
     * Request made by admin
     */
    String IN_ADMIN_PRIORITY_REQUEST_CHANNEL = "admin-priority-request-channel";

    @Input(IN_ADMIN_PRIORITY_REQUEST_CHANNEL)
    SubscribableChannel adminRequest();
}
