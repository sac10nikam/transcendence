package com.nobodyhub.transcendence.api.common.message.trace.config;

import com.nobodyhub.transcendence.api.common.message.trace.EnableMessageTracing;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.MessageExtractorManager;
import com.nobodyhub.transcendence.api.common.message.trace.extractor.TraceTargetExtractor;
import com.nobodyhub.transcendence.api.common.message.trace.processor.MessageTracingPostProcessor;
import com.nobodyhub.transcendence.api.common.message.trace.processor.MessageTracingPreProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

@Slf4j
public class MessageTracingBeansResigtrar implements ImportBeanDefinitionRegistrar {

    @Autowired
    private MessageExtractorManager manager;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata,
                                        BeanDefinitionRegistry registry) {
        AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(
            ClassUtils.resolveClassName(metadata.getClassName(), null),
            EnableMessageTracing.class);

        EnableMessageTracing enableMessageTracing = AnnotationUtils.synthesizeAnnotation(attrs,
            EnableMessageTracing.class, ClassUtils.resolveClassName(metadata.getClassName(), null));

        // default extractor
        Class<? extends TraceTargetExtractor> defaultExtractor = enableMessageTracing.messageExtractor();
        try {
            manager.setDefaultExtractor(defaultExtractor.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("{} needs to have public non-arg contrstuctor!", defaultExtractor.getName());
        }

        // pre-processor
        Class<? extends MessageTracingPreProcessor> preProcessor = enableMessageTracing.preProcessor();
        if (!registry.containsBeanDefinition(preProcessor.getName())) {
            registry.registerBeanDefinition(preProcessor.getName(),
                BeanDefinitionBuilder.rootBeanDefinition(preProcessor).getBeanDefinition());
        }

        // post-processor
        Class<? extends MessageTracingPostProcessor> postProcessor = enableMessageTracing.postProcessor();
        if (!registry.containsBeanDefinition(postProcessor.getName())) {
            registry.registerBeanDefinition(postProcessor.getName(),
                BeanDefinitionBuilder.rootBeanDefinition(postProcessor).getBeanDefinition());
        }
    }
}
