package com.nobodyhub.transcendence.api.executor.client;


import com.google.common.collect.Maps;
import com.nobodyhub.transcendence.common.util.ReflectionUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://docs.spring.io/spring-cloud-stream/docs/Elmhurst.SR2/reference/htmlsingle/">Using Dynamically Bound Destinations</a>
 */
@Slf4j
@Component
public class ClientDispatcher {
    @SuppressWarnings("unchecked")
    private static final Class<? extends Annotation>[] REQUEST_MAPPING_ANNOTATIONS = new Class[]{
        RequestMapping.class,
        GetMapping.class,
        PostMapping.class,
        PutMapping.class,
        DeleteMapping.class,
        PatchMapping.class
    };

    private final ApplicationContext ctx;
    private final RestTemplate restTemplate;
    /**
     * Map destination name to Method
     */
    @Getter
    private Map<String, Method> methodMap = Maps.newHashMap();
    /**
     * Map destination name to Bean
     */
    @Getter
    private Map<String, Object> beanMap = Maps.newHashMap();


    public ClientDispatcher(ApplicationContext ctx, RestTemplate restTemplate) {
        this.ctx = ctx;
        this.restTemplate = restTemplate;
    }

    public void fetchAndDispatch(String destId, String urlTemplate, String urlVariables) {
        Method method = methodMap.get(destId);
        Object bean = beanMap.get(destId);
        if (method == null || bean == null) {
            log.error("Can not find target Method/Bean for destId:[{}].", destId);
            //TODO throw error
        }
        Class<?> responseType = getParameterTypes(destId);
        Object parameter = this.restTemplate.getForObject(urlTemplate, responseType, urlVariables);
        org.springframework.util.ReflectionUtils.invokeMethod(method, bean, parameter);
    }


    public Class<?> getParameterTypes(String destId) {
        Method method = methodMap.get(destId);
        if (method != null) {
            Class<?>[] types = method.getParameterTypes();
            if (types.length == 1) {
                return types[0];
            }
        }
        return Object.class;
    }

    @PostConstruct
    private void initCache() {
        Map<String, Object> clients = ctx.getBeansWithAnnotation(FeignClient.class);
        clients.values().forEach(bean -> {
            Class<?> beanClz = AopProxyUtils.proxiedUserInterfaces(bean)[0];
            String classId = beanClz.getAnnotation(FeignClient.class).name();
            List<Method> methods = ReflectionUtils.getAllMethod(beanClz, REQUEST_MAPPING_ANNOTATIONS);
            methods.forEach(method -> {
                String methodId = getMappingIdentifier(method);
                String destId = getDestId(classId, methodId);
                methodMap.put(destId, method);
                beanMap.put(destId, bean);
            });
        });
    }

    private String getMappingIdentifier(Method method) {
        if (method.getAnnotation(GetMapping.class) != null) {
            // GET
            return method.getAnnotation(GetMapping.class).name();
        } else if (method.getAnnotation(PostMapping.class) != null) {
            // POST
            return method.getAnnotation(PostMapping.class).name();
        } else if (method.getAnnotation(PutMapping.class) != null) {
            // PUT
            return method.getAnnotation(PutMapping.class).name();
        } else if (method.getAnnotation(DeleteMapping.class) != null) {
            // DELETE
            return method.getAnnotation(DeleteMapping.class).name();
        } else if (method.getAnnotation(PatchMapping.class) != null) {
            // PATCH
            return method.getAnnotation(PatchMapping.class).name();
        }
        // Others
        return method.getAnnotation(RequestMapping.class).name();
    }

    private String getDestId(String className, String methodName) {
        return String.format("%s#%s", className, methodName);
    }


}
