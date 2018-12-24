package com.nobodyhub.transcendence.zhihu.common.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
