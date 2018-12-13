package com.nobodyhub.transcendence.zhihu.member.message;

import lombok.Data;

@Data
public class ApiRequestMessage {

    /**
     * type of request, decide which api to call
     */
    private String type;
    /**
     * the variables used in the url template
     */
    private Object[] urlVariables;

    public ApiRequestMessage(String type, Object... urlVariables) {
        this.type = type;
        this.urlVariables = urlVariables;
    }
}
