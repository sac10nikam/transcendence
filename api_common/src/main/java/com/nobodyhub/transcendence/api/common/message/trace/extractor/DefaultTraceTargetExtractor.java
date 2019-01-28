package com.nobodyhub.transcendence.api.common.message.trace.extractor;

import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;

/**
 * The default message extractor
 */
public class DefaultTraceTargetExtractor implements TraceTargetExtractor<ApiRequestMessage> {
    @Override
    public ApiRequestMessage from(ApiRequestMessage target) {
        //return the target directly without any conversion
        return target;
    }
}
