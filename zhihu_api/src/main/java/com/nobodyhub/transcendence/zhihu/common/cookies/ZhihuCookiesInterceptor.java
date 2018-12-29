package com.nobodyhub.transcendence.zhihu.common.cookies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@GlobalChannelInterceptor
public class ZhihuCookiesInterceptor implements ChannelInterceptor {

    private final ZhihuApiCookies cookies;

    public ZhihuCookiesInterceptor(ZhihuApiCookies cookies) {
        this.cookies = cookies;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("In preSend");
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        log.info("In postSend");
        // do nothing
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        log.info("In afterSendCompletion");
        // do nothing
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        log.info("In preReceive");
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        log.info("In postReceive");
        this.cookies.update(message.getHeaders());
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        log.info("In afterReceiveCompletion");
        // do nothing
    }
}
