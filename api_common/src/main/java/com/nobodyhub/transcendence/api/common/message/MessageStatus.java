package com.nobodyhub.transcendence.api.common.message;

import com.nobodyhub.transcendence.message.ApiRequestMessage;
import lombok.Getter;

/**
 * The status of the {@link ApiRequestMessage message}
 */
public enum MessageStatus {
    /**
     * the message is created
     */
    NEW(0, false),
    /**
     * the message is put in the channel
     */
    ENQUEUE(100, false),
    /**
     * the message has passed the throttle
     */
    THROTTLED(110, false),
    /**
     * the request of the message has been sent by the executor
     */
    REQUESTED(200, false),
    /**
     * executor receive response of the message
     */
    RESPONSE_RECEIVED(300, false),
    /**
     * executor send to the reponse channel
     */
    RESPONSE_ENQUEUED(300, false),
    /**
     * the response of the message has processed
     */
    PROCESSED(400, true),
    /**
     * the message has been canceled
     */
    CANCELED(-100, true),
    /**
     * error happened
     */
    FAILED(-200, true),
    ;

    /**
     * Order of the status
     * - positive are for normal status, the smaller ones are in the front, 10 is before 20
     * - negative are for special status
     */
    @Getter
    int order;
    /**
     * Whether the status is an end status
     */
    @Getter
    boolean end;

    MessageStatus(int order, boolean end) {
        this.order = order;
        this.end = end;
    }
}
