package com.nobodyhub.transcendence.api.common.message.trace;

import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.DefaultTraceTargetExtractor;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.TraceTargetExtractor;

import java.lang.annotation.*;

/**
 * Enables the tracing for messages when received by method annotated with {@link MessageTracer}
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableMessageTracing {
    /**
     * Default extractor to parse {@link ApiRequestMessage} from given payload, if extractor is not specified by individual {@link MessageTracer}
     *
     * @return
     */
    Class<? extends TraceTargetExtractor> messageExtractor() default DefaultTraceTargetExtractor.class;

    /**
     * The payload type that will be handled by the {@link #messageExtractor()}
     *
     * @return
     */
    Class<?> payloadType() default ApiRequestMessage.class;
}
