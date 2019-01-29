package com.nobodyhub.transcendence.api.common.message.trace.aop;

import com.nobodyhub.transcendence.api.common.message.ApiRequestMessage;
import com.nobodyhub.transcendence.api.common.message.trace.MessageTracer;
import com.nobodyhub.transcendence.api.common.message.trace.TraceTarget;
import com.nobodyhub.transcendence.api.common.message.trace.config.MessageTracerAnnotationBeanPostProcessor;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.TraceTargetExtractor;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.TraceTargetExtractorManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Aspect for message tracing
 */
@Aspect
public class MessageTraceAspect {
    @Autowired
    private TraceTargetExtractorManager traceTargetExtractorManager;

    @Around("@annotation(com.nobodyhub.transcendence.api.common.message.trace.MessageTracer)")
    public Object traceMessage(ProceedingJoinPoint joinPoint) throws Throwable {
        MessageTracer messageTracer = getMessageTracer(joinPoint);
        TraceTargetExtractor extractor = getExtractor(joinPoint);
        Object payload = getPayload(joinPoint);
        ApiRequestMessage message = extractor.from(payload);
        if (!StringUtils.isEmpty(messageTracer.beginStatus())) {
            // TODO: process begin status
        }


        Object rVal = joinPoint.proceed();
        if (!StringUtils.isEmpty(messageTracer.status())
            || !StringUtils.isEmpty(messageTracer.endStatus())) {
            String endStatus = StringUtils.isEmpty(messageTracer.status()) ? messageTracer.status() : messageTracer.endStatus();
            // TODO: process end status
        }
        return rVal;
    }

    private TraceTargetExtractor getExtractor(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return traceTargetExtractorManager.getExtractor(method);
    }

    /**
     * Get the payload from the parameter
     * <p>
     * the existence has been guarenteed in the {@link MessageTracerAnnotationBeanPostProcessor#postProcessAfterInitialization(Object, String) initialize} process
     *
     * @param joinPoint
     * @return
     * @see
     */
    private Object getPayload(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();

        int paramIdx = 0;
        for (Parameter parameter : parameters) {
            TraceTarget anno = parameter.getAnnotation(TraceTarget.class);
            if (anno != null) {
                break;
            }
            paramIdx++;
        }
        return joinPoint.getArgs()[paramIdx];
    }

    private MessageTracer getMessageTracer(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(MessageTracer.class);
    }
}
