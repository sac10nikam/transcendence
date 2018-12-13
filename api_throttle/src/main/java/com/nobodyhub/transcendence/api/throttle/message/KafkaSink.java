package com.nobodyhub.transcendence.api.throttle.message;

import com.nobodyhub.transcendence.api.throttle.Client.ApiServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@Slf4j
@EnableBinding(Sink.class)
public class KafkaSink {
    private final long interval;
    private final ApiServiceClient apiServiceClient;

    public KafkaSink(@Value("${transcendence.api.throttle.interval}") int interval,
                     ApiServiceClient apiServiceClient) {
        this.interval = interval;
        this.apiServiceClient = apiServiceClient;
    }

    /**
     * request api service to access the url
     *
     * @param message
     */
    @StreamListener(Sink.INPUT)
    public void receiveRequest(ApiRequestMessage message) {
        apiServiceClient.doRequest(message);
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            log.warn("Sleep during throttle.interval interrupted with error: {}", e);
            Thread.currentThread().interrupt();
        }
    }
}
