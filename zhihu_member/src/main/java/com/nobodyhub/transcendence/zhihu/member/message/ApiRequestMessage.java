package com.nobodyhub.transcendence.zhihu.member.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiRequestMessage {

    /**
     * the url to request
     * TODO: add cookies support
     */
    private String url;
    /**
     * topic to cache the response
     */
    private String topic;
}
