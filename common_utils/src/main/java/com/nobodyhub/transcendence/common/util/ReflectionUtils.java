package com.nobodyhub.transcendence.common.util;

import com.google.common.collect.Lists;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    /**
     * Get all public/protected/private fields of given class
     *
     * @param clz
     * @return
     */
    public static List<Field> getAllFields(Class<?> clz) {
        List<Field> fields = Lists.newArrayList();
        Class curClz = clz;
        while (curClz != null && !Object.class.equals(curClz)) {
            for (Field field : curClz.getDeclaredFields()) {
                field.setAccessible(true);
                fields.add(field);
            }
            curClz = curClz.getSuperclass();
        }
        return fields;
    }

    /**
     * Get all <b>public</b> methods with any of given annotation
     *
     * @param clz
     * @param annos a list of annotation
     * @return
     */
    public static List<Method> getAllMethod(Class<?> clz, Class<? extends Annotation>... annos) {
        List<Method> methods = Lists.newArrayList();
        Class curClz = clz;
        while (curClz != null && !Object.class.equals(curClz)) {
            for (Method method : curClz.getMethods()) {
                for (Class<? extends Annotation> anno : annos) {
                    if (method.getAnnotation(anno) != null) {
                        method.setAccessible(true);
                        methods.add(method);
                        break;
                    }
                }
            }
            curClz = curClz.getSuperclass();
        }
        return methods;
    }
}
