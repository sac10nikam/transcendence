package com.nobodyhub.transcendence.api.throttle.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ApiRequestMessage {

    /**
     * the url to request
     * TODO: add cookies support
     */
    private String url;
    /**
     * qualifier for the response handler
     */
    private String destId;
}
