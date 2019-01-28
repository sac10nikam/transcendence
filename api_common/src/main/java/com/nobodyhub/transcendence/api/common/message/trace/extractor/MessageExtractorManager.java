package com.nobodyhub.transcendence.api.common.message.trace.extractor;

import com.google.common.collect.Maps;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Map;

@Data
public class MessageExtractorManager {
    private Map<Method, TraceTargetExtractor> extractors = Maps.newHashMap();
    private TraceTargetExtractor defaultExtractor;

    public TraceTargetExtractor getExtractor(Method method) {
        TraceTargetExtractor extractor = extractors.get(method);
        if (extractor != null) {
            return extractor;
        }
        return defaultExtractor;
    }

    public void setDefaultExtractor(TraceTargetExtractor extractor) {
        this.defaultExtractor = extractor;
    }

    public void addExtractor(Method method, TraceTargetExtractor extractor) {
        this.extractors.put(method, extractor);
    }
}
