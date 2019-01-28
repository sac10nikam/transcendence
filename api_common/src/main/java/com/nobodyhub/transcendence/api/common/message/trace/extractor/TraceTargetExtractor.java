package com.nobodyhub.transcendence.api.common.message.trace.extractor;

import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;

/**
 * Extract {@link ApiRequestMessage} from given target
 *
 * @param <T> payload type
 */
public interface TraceTargetExtractor<T> {

    /**
     * extract message from target object
     *
     * @param target
     * @return
     */
    ApiRequestMessage from(T target);
}
