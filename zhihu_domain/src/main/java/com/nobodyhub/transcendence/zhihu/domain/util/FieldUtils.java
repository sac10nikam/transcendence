package com.nobodyhub.transcendence.zhihu.domain.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public final class FieldUtils {
    private FieldUtils() {
    }

    /**
     * filter out unnecessary field values by setting them to null
     *
     * @param object   the object to be filterred
     * @param includes the field names to be included
     */
    public static <T> T filter(T object, Set<String> includes) {
        ReflectionUtils.getAllFields(object.getClass()).forEach(field -> {
            if (!includes.contains(field.getName())) {
                try {
                    field.set(object, null);
                } catch (IllegalAccessException e) {
                    log.error("Error happens when trying to set Field[{}] of Class[{}] to null!",
                        field.getName(), object.getClass());
                    log.error(e.getMessage());
                }
            }
        });
        return object;
    }
}
