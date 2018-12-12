package com.nobodyhub.transcendence.common.merge;

import com.nobodyhub.transcendence.common.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
public final class MergeUtils {
    private MergeUtils() {
    }

    /**
     * Merge 2 object
     * - if field of new object is null, use old value
     * - if field is Mergeable, merge the value and use the merged value
     * - otherwise, use the new value.
     *
     * @param newObject new Object, not null
     * @param oldObject old Ojbect, not null
     * @param <T>       subclass of {@link Mergeable}
     * @return the merged object(based on the old object)
     */
    public static <T extends Mergeable> T merge(T newObject, T oldObject) {
        if (newObject == null) {
            return oldObject;
        }
        if (oldObject == null) {
            return newObject;
        }

        @SuppressWarnings("unchecked")
        Class<T> clz = (Class<T>) newObject.getClass();
        List<Field> fields = ReflectionUtils.getAllFields(clz);
        // update based on the old object
        T result = oldObject;
        try {
            for (Field field : fields) {
                if (field.get(newObject) == null) {
                    //skip if the field of new object is null
                    continue;
                }
                if (Mergeable.class.isAssignableFrom(field.getType())) {
                    // if field is subclass of Mergeable
                    // use the merge value
                    field.set(result, merge((Mergeable) field.get(newObject), (Mergeable) field.get(oldObject)));
                } else {
                    // - if field is subclass of Collections, e.g., List, Set
                    // - if field is subclass of Map, e.g., HashMap
                    // - remaining case, e.g., primitive types, String
                    //  => use the new value
                    field.set(result, field.get(newObject));
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Error happens when merge class:[{}]", clz.getName());
            log.error(e.getMessage());
        }
        return result;
    }


}
