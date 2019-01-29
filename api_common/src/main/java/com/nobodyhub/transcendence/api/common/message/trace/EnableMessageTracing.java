package com.nobodyhub.transcendence.api.common.message.trace;

import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.api.common.message.trace.config.MessageTracingBeansResigtrar;
import com.nobodyhub.transcendence.api.common.message.trace.config.MessageTracingConfiguration;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.TraceTargetExtractor;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.impl.DefaultTraceTargetExtractor;
import com.nobodyhub.transcendence.api.common.message.trace.processor.MessageTracingPostProcessor;
import com.nobodyhub.transcendence.api.common.message.trace.processor.MessageTracingPreProcessor;
import com.nobodyhub.transcendence.api.common.message.trace.processor.impl.DefaultMessageTracingPostProcessor;
import com.nobodyhub.transcendence.api.common.message.trace.processor.impl.DefaultMessageTracingPreProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables the tracing for messages when received by method annotated with {@link MessageTracer}
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MessageTracingConfiguration.class, MessageTracingBeansResigtrar.class})
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

    /**
     * Pre-processor for message tracing
     * <p>
     * By default, doing nothing(all process is done by the {@link #postProcessor() post-processor}
     */
    Class<? extends MessageTracingPreProcessor> preProcessor() default DefaultMessageTracingPreProcessor.class;

    /**
     * Post-processor for message tracing
     */
    Class<? extends MessageTracingPostProcessor> postProcessor() default DefaultMessageTracingPostProcessor.class;
}
