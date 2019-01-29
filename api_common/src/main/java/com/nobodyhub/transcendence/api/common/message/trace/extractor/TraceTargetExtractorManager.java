package com.nobodyhub.transcendence.api.common.message.trace.extractor;

import com.google.common.collect.Maps;
import com.nobodyhub.transcendence.api.common.message.trace.exception.MessageTracingInitializationException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.reflect.Method;
import java.util.Map;

import static com.nobodyhub.transcendence.api.common.message.trace.common.MessageTracingConst.DEFAULT_EXTRACTOR_QUALIFIER;

@Slf4j
@Data
public class TraceTargetExtractorManager {
    private Map<Method, TraceTargetExtractor> extractors = Maps.newHashMap();

    @Autowired
    @Qualifier(DEFAULT_EXTRACTOR_QUALIFIER)
    private TraceTargetExtractor defaultExtractor;

    public TraceTargetExtractor getExtractor(Method method) {
        TraceTargetExtractor extractor = extractors.get(method);
        if (extractor != null) {
            return extractor;
        }
        return defaultExtractor;
    }

    public void addExtractor(Method method, Class<TraceTargetExtractor> extractorClz) {
        this.extractors.put(method, createInstance(extractorClz));
    }

    private TraceTargetExtractor createInstance(Class<TraceTargetExtractor> clz) {
        try {
            return clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("{} needs to have public non-arg contrstuctor!", clz.getName());
            log.error("Fail to register bean due to error: ", e);
            throw new MessageTracingInitializationException(e);
        }
    }
}
