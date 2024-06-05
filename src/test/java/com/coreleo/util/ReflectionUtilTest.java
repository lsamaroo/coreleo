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
            public String field = "";

            @Override
            public String toString() {
                return this.field + "";
            }
        };

        ReflectionUtil.setFieldValue(obj, "field", "TEST");
        assertEquals("TEST", obj.toString());
    }

    @Test
    void testGetFieldValue() {
        final Object obj = new Object() {
            public String field = "Test";

            @Override
            public String toString() {
                return this.field + "";
            }
        };

        assertEquals("Test", ReflectionUtil.getFieldValue(obj, "field"));
    }

    @Test
    void testInvokeObjectString() {
        final List<String> list = new ArrayList<>();
        list.add("Test");
        final var size = ReflectionUtil.invoke(list, "size");
        assertEquals("1", String.valueOf(size));
    }

    @Test
    void testInvokeObjectStringObjectArray() {
        final List<String> list = new ArrayList<>();
        list.add("Test");
        ReflectionUtil.invoke(list, "add", "TEST2");
        assertEquals(2, list.size());
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
            private boolean b = false;

            public void setB(final boolean b) {
                this.b = b;
            }

            @Override
            public String toString() {
                this.setB(true);
                return this.b + "";
            }
        };

        ReflectionUtil.invoke(obj, "setB", true);
        assertEquals("true", obj.toString());
    }

}
