package com.nobodyhub.transcendence.zhihu.domain.util;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FieldUtilsTest {
    @Test
    public void filterTest() {
        A a = new A("a1",
            2,
            new B("b1"),
            new B("b2"));
        FieldUtils.filter(a, Sets.newHashSet("a2", "b2"));
        assertEquals(null, a.getA1());
        assertEquals(2, a.getA2());
        assertEquals(null, a.getB1());
        assertEquals(new B("b2"), a.getB2());
    }

    @Data
    @AllArgsConstructor
    static class A {
        private String a1;
        private int a2;
        private B b1;
        private B b2;
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    static class B {
        private String b;
    }
}
