package com.nobodyhub.transcendence.common.merge;

import com.nobodyhub.transcendence.common.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
public final class MergeUtils {
    private MergeUtils() {
    }

    public static <T extends Mergeable> T merge(T newObject, T oldObject) {
        return merge(newObject, oldObject, true);
    }

    /**
     * Merge 2 object
     * - if field of new object is null, use old value
     * - if field is Mergeable, merge the value and use the merged value
     * - otherwise, use the new value.
     *
     * @param newObject       new Object, not null
     * @param oldObject       old Ojbect, not null
     * @param mergeCollection whether to merge collection, if no, old collection will be override by new one
     * @param <T>             subclass of {@link Mergeable}
     * @return the merged object(based on the old object)
     */
    public static <T extends Mergeable> T merge(T newObject, T oldObject, boolean mergeCollection) {
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
                if (field.get(oldObject) == null) {
                    // if old object is null, set to new object value
                    field.set(result, field.get(newObject));
                    continue;
                }
                if (Mergeable.class.isAssignableFrom(field.getType())) {
                    // if field is subclass of Mergeable
                    // use the merge value
                    field.set(result, merge((Mergeable) field.get(newObject), (Mergeable) field.get(oldObject)));
                } else {
                    if (mergeCollection
                        // - if field is subclass of Map, e.g., HashMap
                        && Map.class.isAssignableFrom(field.getType())) {
                        Map newValue = (Map) field.get(newObject);
                        Map oldValue = (Map) field.get(oldObject);
                        newValue.putAll(oldValue);
                        field.set(result, newValue);
                    } else if (mergeCollection
                        && Collection.class.isAssignableFrom(field.getType())) {
                        // - if field is subclass of Collections, e.g., List, Set
                        Collection newValue = (Collection) field.get(newObject);
                        Collection oldValue = (Collection) field.get(oldObject);
                        newValue.addAll(oldValue);
                        field.set(result, newValue);
                    } else {
                        // - remaining case, e.g., primitive types, String
                        //  => use the new value
                        field.set(result, field.get(newObject));
                    }


                }
            }
        } catch (IllegalAccessException e) {
            log.error("Error happens when merge class:[{}]", clz.getName());
            log.error(e.getMessage());
        }
        return result;
    }


}
