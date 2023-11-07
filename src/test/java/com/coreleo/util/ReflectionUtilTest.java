package com.coreleo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ReflectionUtilTest {

    @Test
    void testIsInstance() {
        final Object obj = "Test";
        assertTrue(ReflectionUtil.isInstance("java.lang.String", obj));
    }

    @Test
    void testForName() {
        final Object obj = ReflectionUtil.forName("java.lang.String");
        assertTrue(obj != null);
    }

    @Test
    void testInvokeStatic() {
        final var obj = ReflectionUtil.invokeStatic("java.util.Calendar", "getInstance");
        System.out.println(obj);
        assertTrue(obj != null);
    }

    @Test
    void testNewInstanceString() {
        final var obj = ReflectionUtil.newInstance("java.lang.String");
        assertTrue(obj instanceof String);
    }

    @Test
    void testNewInstanceStringObjectArray() {
        final var obj = ReflectionUtil.newInstance("java.lang.String", "Test");
        assertTrue(obj.equals("Test"));
    }

    @Test
    void testSetFieldValue() {
        final Object obj = new Object() {
            String field = "";

            @Override
            public String toString() {
                return this.field + "";
            }
        };

        ReflectionUtil.setFieldValue(obj, "field", "TEST");
        assertEquals(obj.toString(), "TEST");
    }

    @Test
    void testGetFieldValue() {
        final Object obj = new Object() {
        };

        assertEquals(ReflectionUtil.getFieldValue(obj, "field"), "Test");
    }

    @Test
    void testInvokeObjectString() {
        final List<String> list = new ArrayList<>();
        list.add("Test");
        final var size = ReflectionUtil.invoke(list, "size");
        assertTrue("1".equals(String.valueOf(size)));
    }

    @Test
    void testInvokeObjectStringObjectArray() {
        final List<String> list = new ArrayList<>();
        list.add("Test");
        ReflectionUtil.invoke(list, "add", "TEST2");
        assertTrue(list.size() == 2);
    }

    @Test
    void testInvokeObjectStringPrimitiveInt() {
        final List<String> list = new ArrayList<>();
        list.add("Test");
        final var item = ReflectionUtil.invoke(list, "get", 0);
        assertTrue("Test".equals(String.valueOf(item)));
    }

    @Test
    void testInvokeObjectStringPrimitiveBool() {
        final Object obj = new Object() {
            private final boolean b = false;

            @Override
            public String toString() {
                return this.b + "";
            }
        };

        ReflectionUtil.invoke(obj, "set", true);
        assertEquals("true", obj.toString());
    }

}
