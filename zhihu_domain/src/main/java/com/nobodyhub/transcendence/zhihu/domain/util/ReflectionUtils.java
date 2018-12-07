package com.nobodyhub.transcendence.zhihu.domain.util;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    /**
     * Get all fields of given class
     *
     * @param clz
     * @return
     */
    public static List<Field> getAllFields(Class<?> clz) {
        List<Field> fields = Lists.newArrayList();
        Class curClz = clz;
        while (!curClz.equals(Object.class)) {
            for (Field field : curClz.getDeclaredFields()) {
                field.setAccessible(true);
                fields.add(field);
            }
            curClz = curClz.getSuperclass();
        }
        return fields;
    }
}
