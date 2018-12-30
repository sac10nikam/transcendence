package com.nobodyhub.transcendence.common.merge;

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
            new B("bOLdList3", null)
        ));
        Map<String, B> oldBMap = Maps.newHashMap();
        oldBMap.put("oldB", new B("bOld", false));
        oldA.setBMap(oldBMap);
        oldA.setC(new C("cOld", 1L));

        A newA = new A();
        newA.setA1("aNew");
        newA.setB(new B("bNew", null));
        newA.setBList(Lists.newArrayList(
            new B("bNewList1", null),
            new B("bNewList2", true),
            new B("bNewList3", false)
        ));
        Map<String, B> newBMap = Maps.newHashMap();
        newBMap.put("newB1", new B("bNew1", false));
        newBMap.put("newB2", new B("bNew2", false));
        newA.setBMap(newBMap);
        newA.setC(new C("cNew", 2L));

        A result = MergeUtils.merge(newA, oldA);
        assertEquals(oldA, result);
        assertEquals("sOld", result.getS());
        assertEquals("aNew", result.getA1());
        assertEquals(Integer.valueOf(1), result.getA2());
        assertEquals(new B("bNew", true), result.getB());
        assertEquals(6, result.getBList().size());
        assertEquals(true, result.getBList().contains(new B("bOLdList1", true)));
        assertEquals(true, result.getBList().contains(new B("bOLdList2", false)));
        assertEquals(true, result.getBList().contains(new B("bOLdList3", null)));
        assertEquals(true, result.getBList().contains(new B("bNewList1", null)));
        assertEquals(true, result.getBList().contains(new B("bNewList2", true)));
        assertEquals(true, result.getBList().contains(new B("bNewList3", false)));
        assertEquals(3, result.getBMap().size());
        assertEquals(true, result.getBMap().containsKey("oldB"));
        assertEquals(true, result.getBMap().containsKey("newB1"));
        assertEquals(true, result.getBMap().containsKey("newB2"));
        assertEquals(new C("cNew", 2L), result.getC());

        result = MergeUtils.merge(newA, null);
        assertEquals(newA, result);

        result = MergeUtils.merge(null, oldA);
        assertEquals(oldA, result);

        result = MergeUtils.merge(null, null);
        assertEquals(null, result);
    }

    @Data
    static class S implements Mergeable {
        private String s;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
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


