package com.nobodyhub.transcendence.message.tracer.extractor;

import com.nobodyhub.transcendence.message.ApiRequestMessage;

/**
 * Extract {@link ApiRequestMessage} from given target
 * The extractor needs to have a public non-arg constructor
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
