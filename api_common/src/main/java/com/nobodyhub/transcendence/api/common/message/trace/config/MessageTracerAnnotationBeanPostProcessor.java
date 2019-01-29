package com.nobodyhub.transcendence.api.common.message.trace.config;

import com.nobodyhub.transcendence.api.common.message.trace.MessageTracer;
import com.nobodyhub.transcendence.api.common.message.trace.TraceTarget;
import com.nobodyhub.transcendence.api.common.message.trace.exception.MessageTracingInitializationException;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.TraceTargetExtractor;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.TraceTargetExtractorManager;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MessageTracerAnnotationBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private TraceTargetExtractorManager extractorManager;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        extractorManager = applicationContext.getBean(TraceTargetExtractorManager.class);
    }

    @Override
    public final Object postProcessAfterInitialization(Object bean, final String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.isAopProxy(bean) ? AopUtils.getTargetClass(bean) : bean.getClass();
        Method[] uniqueDeclaredMethods = ReflectionUtils.getUniqueDeclaredMethods(targetClass);
        for (Method method : uniqueDeclaredMethods) {
            MessageTracer messageTracer = AnnotatedElementUtils.findMergedAnnotation(method, MessageTracer.class);
            if (messageTracer != null && !method.isBridge()) {
                Parameter[] parameters = method.getParameters();
                if (parameters.length == 0) {
                    throw new MessageTracingInitializationException(
                        String.format("The method that annotated with %s needs to have at least 1 parameter!",
                            MessageTracer.class.getName()));
                }
                int annotatedCount = 0;
                for (Parameter parameter : parameters) {
                    TraceTarget annotation = parameter.getAnnotation(TraceTarget.class);
                    if (annotation != null) {
                        annotatedCount++;
                    }
                }
                if (annotatedCount > 1) {
                    throw new MessageTracingInitializationException(
                        String.format("Only 1 parameter can be annotated with %s!",
                            TraceTarget.class.getName()));
                }
                if (annotatedCount == 0 && parameters.length != 1) {
                    throw new MessageTracingInitializationException(
                        String.format("At least 1 parameter should be annotated with %s!",
                            TraceTarget.class.getName()));
                }
                // add method specific extractor
                Class<TraceTargetExtractor> extractor = messageTracer.extractor();
                this.extractorManager.addExtractor(method, extractor);
            }
        }
        return bean;
    }
}
