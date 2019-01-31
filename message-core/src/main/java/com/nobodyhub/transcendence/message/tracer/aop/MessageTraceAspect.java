package com.nobodyhub.transcendence.message.tracer.aop;

import com.google.common.collect.Maps;
import com.nobodyhub.transcendence.message.ApiRequestMessage;
import com.nobodyhub.transcendence.message.tracer.MessageTracer;
import com.nobodyhub.transcendence.message.tracer.TraceTarget;
import com.nobodyhub.transcendence.message.tracer.config.MessageTracerAnnotationBeanPostProcessor;
import com.nobodyhub.transcendence.message.tracer.extractor.TraceTargetExtractor;
import com.nobodyhub.transcendence.message.tracer.extractor.TraceTargetExtractorManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.nobodyhub.transcendence.message.tracer.common.MessageTracingConst.*;

/**
 * Aspect for message tracing
 */
@Aspect
public class MessageTraceAspect {
    @Autowired
    private TraceTargetExtractorManager traceTargetExtractorManager;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${message.trace.delay.remove}")
    private long removeDelay;

    @Around("@annotation(com.nobodyhub.transcendence.message.tracer.MessageTracer)")
    public Object traceMessage(ProceedingJoinPoint joinPoint) throws Throwable {
        MessageTracer messageTracer = getMessageTracer(joinPoint);
        TraceTargetExtractor extractor = getExtractor(joinPoint);
        Object payload = getPayload(joinPoint);
        @SuppressWarnings("unchecked")
        ApiRequestMessage message = extractor.from(payload);
        // create for new trace
        if (messageTracer.isStart()) {
            Map<String, String> updates = Maps.newHashMap();
            updates.put(TRACE_GROUP, message.getGroup());
            updates.put(TRACE_CREATED_AT, String.valueOf(message.getCreatedAt()));

            redisTemplate.<String, String>boundHashOps(message.getUid())
                .putAll(updates);
        }

        // update begin status
        if (!StringUtils.isEmpty(messageTracer.beginStatus())) {
            redisTemplate.<String, String>boundHashOps(message.getUid())
                .put(TRACE_STATUS, messageTracer.beginStatus());
        }

        // process the message
        Object rVal = joinPoint.proceed();

        // update end status
        if (!StringUtils.isEmpty(messageTracer.status())
            || !StringUtils.isEmpty(messageTracer.endStatus())) {
            String endStatus = StringUtils.isEmpty(messageTracer.status()) ? messageTracer.status() : messageTracer.endStatus();
            redisTemplate.<String, String>boundHashOps(message.getUid())
                .put(TRACE_STATUS, endStatus);
        }

        // expire the message when message lifecycle ends
        if (messageTracer.isEnd()) {
            redisTemplate.<String, String>boundHashOps(message.getUid())
                .expire(removeDelay, TimeUnit.SECONDS);
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
