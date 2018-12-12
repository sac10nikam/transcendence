package com.nobodyhub.transcendence.common.util;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReflectionUtilsTest {
    @Test
    public void getAllFieldsTest() {
        List<Field> fields = ReflectionUtils.getAllFields(A.class);
        assertEquals(6, fields.size());
        Set<String> expected = Sets.newHashSet("s", "a1", "a2", "b", "bList", "bMap");
        for (Field field : fields) {
            assertTrue(expected.contains(field.getName()));
        }
    }

    @Data
    static class S {
        private String s;
    }

    @Data
    static class A extends S {
        private String a1;
        private Integer a2;
        private B b;
        private List<B> bList;
        private Map<String, B> bMap;
    }

    @Data
    @EqualsAndHashCode
    @AllArgsConstructor
    static class B {
        private String b1;
        private Boolean b2;
    }
}
