package com.coreleo.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ReflectionUtilTest
{

	@Test
	public void testIsInstance()
	{
		final Object obj = new String("Test");
		assertTrue(ReflectionUtil.isInstance("java.lang.String", obj));
	}

	@Test
	public void testForName()
	{
		final Object obj = ReflectionUtil.forName("java.lang.String");
		assertTrue(obj != null);
	}

	@Test
	public void testInvokeStatic()
	{
		final Object obj = ReflectionUtil.invokeStatic("java.util.Calendar", "getInstance");
		System.out.println(obj);
		assertTrue(obj != null);
	}

	@Test
	public void testNewInstanceString()
	{
		final Object obj = ReflectionUtil.newInstance("java.lang.String");
		assertTrue(obj instanceof String);
	}

	@Test
	public void testNewInstanceStringObjectArray()
	{
		final Object obj = ReflectionUtil.newInstance("java.lang.String", "Test");
		assertTrue(obj.equals("Test"));
	}

	@Test
	public void testSetFieldValue()
	{
		final Object obj = new Object()
		{
			public String field = "";

			@Override
			public String toString()
			{
				return field + "";
			}
		};

		ReflectionUtil.setFieldValue(obj, "field", "TEST");
		assertEquals(obj.toString(), "TEST");
	}

	@Test
	public void testGetFieldValue()
	{
		final Object obj = new Object()
		{
			@SuppressWarnings("unused")
			public String field = "Test";
		};

		assertEquals(ReflectionUtil.getFieldValue(obj, "field"), "Test");
	}

	@Test
	public void testInvokeObjectString()
	{
		final List<String> list = new ArrayList<String>();
		list.add("Test");
		final Object size = ReflectionUtil.invoke(list, "size");
		assertTrue("1".equals(String.valueOf(size)));
	}

	@Test
	public void testInvokeObjectStringObjectArray()
	{
		final List<String> list = new ArrayList<String>();
		list.add("Test");
		ReflectionUtil.invoke(list, "add", "TEST2");
		assertTrue(list.size() == 2);
	}

	@Test
	public void testInvokeObjectStringPrimitive()
	{
		final List<String> list = new ArrayList<String>();
		list.add("Test");
		final Object item = ReflectionUtil.invoke(list, "get", 0);
		assertTrue("Test".equals(String.valueOf(item)));
	}

}
