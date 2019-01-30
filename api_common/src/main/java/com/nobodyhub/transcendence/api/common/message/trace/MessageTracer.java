package com.nobodyhub.transcendence.api.common.message.trace;

import com.nobodyhub.transcendence.api.common.message.trace.extractor.TraceTargetExtractor;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Annotation that marks a method as message tracer
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageTracer {

    /**
     * The message status after being processed by annotated method
     *
     * @see #endStatus()
     */
    @AliasFor("endStatus")
    String status();

    /**
     * The message status before being processed by annotated method
     */
    String beginStatus();

    /**
     * The message status after being processed by annotated method
     *
     * @return
     */
    String endStatus();

    /**
     * Specify individual the message extractor, if not specified, will use the default one by {@link EnableMessageTracing#messageExtractor()}
     *
     * @return
     */
    Class<TraceTargetExtractor> extractor() default TraceTargetExtractor.class;

    /**
     * whether annotated method is the end of message
     */
    boolean isEnd() default false;

    /**
     * whether annotated method is the start of message
     */
    boolean isStart() default false;
}
