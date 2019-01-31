package com.nobodyhub.transcendence.message.tracer.extractor.impl;

import com.nobodyhub.transcendence.message.ApiRequestMessage;
import com.nobodyhub.transcendence.message.tracer.extractor.TraceTargetExtractor;

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
