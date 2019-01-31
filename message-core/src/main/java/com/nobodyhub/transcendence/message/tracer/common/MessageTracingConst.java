package com.nobodyhub.transcendence.message.tracer.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageTracingConst {
    /**
     * The bean name of the default message extractor
     */
    public static final String DEFAULT_EXTRACTOR_QUALIFIER = "messssage-tracing-default-extractor";

    /**
     * Redis trace hash key
     */
    public static final String TRACE_GROUP = "trace.group";
    public static final String TRACE_STATUS = "trace.status";
    public static final String TRACE_CREATED_AT = "trace.created-at";
}
