package com.nobodyhub.transcendence.api.common.message.trace;

import java.lang.annotation.*;

/**
 * Mark the parameter as the trace targer
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TraceTarget {
}
