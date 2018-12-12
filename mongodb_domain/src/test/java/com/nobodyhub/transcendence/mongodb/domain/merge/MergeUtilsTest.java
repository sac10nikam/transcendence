package com.nobodyhub.transcendence.mongodb.domain.merge;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MergeUtilsTest {

    @Test
    public void mergeTest() {
        A oldA = new A();
        oldA.setS("sOld");
        oldA.setA2(1);
        oldA.setB(new B("bOld", true));
        oldA.setBList(Lists.newArrayList(
            new B("bOLdList1", true),
            new B("bOLdList2", false),
            new B("bOLdList2", null)
        ));
        oldA.setBMap(Maps.newHashMap());
        oldA.setC(new C("cOld", 1L));

        A newA = new A();
        newA.setA1("aNew");
        newA.setB(new B("bNew", null));
        newA.setBList(Lists.newArrayList(
            new B("bNewList1", null),
            new B("bNewList2", true),
            new B("bNewList3", false)
        ));
        newA.setC(new C("cNew", 2L));

        A result = MergeUtils.merge(newA, oldA);
        assertEquals(oldA, result);
        assertEquals("sOld", result.getS());
        assertEquals("aNew", result.getA1());
        assertEquals(Integer.valueOf(1), result.getA2());
        assertEquals(new B("bNew", true), result.getB());
        assertEquals(3, result.getBList().size());
        assertEquals(true, result.getBList().contains(new B("bNewList1", null)));
        assertEquals(true, result.getBList().contains(new B("bNewList2", true)));
        assertEquals(true, result.getBList().contains(new B("bNewList3", false)));
        assertEquals(true, result.getBMap().isEmpty());
        assertEquals(new C("cNew", 2L), result.getC());

    }

    @Data
    static class S implements Mergeable {
        private String s;
    }

    @Data
    static class A extends S {
        private String a1;
        private Integer a2;
        private B b;
        private List<B> bList;
        private Map<String, B> bMap;
        private C c;
    }

    @Data
    @EqualsAndHashCode
    @AllArgsConstructor
    static class B implements Mergeable {
        private String b1;
        private Boolean b2;
    }

    @Data
    @EqualsAndHashCode
    @AllArgsConstructor
    static class C {
        private String c1;
        private Long c2;
    }
}


