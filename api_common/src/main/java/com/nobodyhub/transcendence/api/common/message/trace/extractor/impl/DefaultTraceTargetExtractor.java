package com.nobodyhub.transcendence.api.common.message.trace.extractor.impl;

import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.TraceTargetExtractor;

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
