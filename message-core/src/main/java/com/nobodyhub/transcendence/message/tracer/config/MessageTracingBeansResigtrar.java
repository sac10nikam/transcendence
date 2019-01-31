package com.nobodyhub.transcendence.message.tracer.config;

import com.nobodyhub.transcendence.message.tracer.EnableMessageTracing;
import com.nobodyhub.transcendence.message.tracer.extractor.TraceTargetExtractor;
import com.nobodyhub.transcendence.message.tracer.processor.MessageTracingPostProcessor;
import com.nobodyhub.transcendence.message.tracer.processor.MessageTracingPreProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import static com.nobodyhub.transcendence.message.tracer.common.MessageTracingConst.DEFAULT_EXTRACTOR_QUALIFIER;

@Slf4j
public class MessageTracingBeansResigtrar implements ImportBeanDefinitionRegistrar {

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
        if (!registry.containsBeanDefinition(defaultExtractor.getName())) {
            RootBeanDefinition defaultExtractorBean = new RootBeanDefinition(defaultExtractor);
            defaultExtractorBean.addQualifier(new AutowireCandidateQualifier(DEFAULT_EXTRACTOR_QUALIFIER));
            registry.registerBeanDefinition(defaultExtractor.getName(), defaultExtractorBean);
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
