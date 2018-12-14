package com.nobodyhub.transcendence.common.util;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

    @Test
    public void getAllMethodTest() {
        List<Method> methods = ReflectionUtils.getAllMethod(A.class, Anno.class);
        assertEquals(2, methods.size());
        Set<String> expected = Sets.newHashSet("funcPublicA", "funcProtectedS");
        for (Method method : methods) {
            assertTrue(expected.contains(method.getName()));
        }
    }

    @Data
    static class S {
        private String s;

        @Anno
        private void funcPrivateS() {

        }

        @Anno
        protected void funcProtectedS() {

        }

        public void funcPublicS() {

        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    static class A extends S {
        private String a1;
        private Integer a2;
        private B b;
        private List<B> bList;
        private Map<String, B> bMap;

        @Anno
        private void funcPrivateA() {

        }

        protected void funcProtectedA() {

        }

        @Anno
        public void funcPublicA() {

        }

        @Anno
        public void funcProtectedS() {

        }
    }

    @Data
    @EqualsAndHashCode
    @AllArgsConstructor
    static class B {
        private String b1;
        private Boolean b2;

        private void funcB() {

        }
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Anno {

    }
}
