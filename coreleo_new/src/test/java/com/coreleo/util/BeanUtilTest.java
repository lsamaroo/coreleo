package com.coreleo.util;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BeanUtilTest
{

	@Test
	public void testToMap()
	{
		final BeanPatternTest bean = new BeanPatternTest();
		bean.setBooleanValue(true);
		bean.setIntValue(8);
		bean.setStringValue("leon");

		final Map<String, Object> map = BeanUtil.toMap(bean);

		assertTrue(map.get("intValue") != null);
		assertTrue(map.get("stringValue") != null);
		assertTrue(map.get("booleanValue") != null);
	}

	@Test
	public void testPopulateBean()
	{
		final BeanPatternTest bean = new BeanPatternTest();
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("intValue", 8);
		map.put("stringValue", "Leon");
		map.put("booleanValue", true);

		BeanUtil.populateBean(bean, map);

		assertTrue(bean.toString().equals("Leon8true"));
	}

	public class BeanPatternTest
	{
		private String stringValue;
		private int intValue;
		private boolean booleanValue;

		public String getStringValue()
		{
			return stringValue;
		}

		public void setStringValue(String stringValue)
		{
			this.stringValue = stringValue;
		}

		public int getIntValue()
		{
			return intValue;
		}

		public void setIntValue(int intValue)
		{
			this.intValue = intValue;
		}

		public boolean isBooleanValue()
		{
			return booleanValue;
		}

		public void setBooleanValue(boolean booleanValue)
		{
			this.booleanValue = booleanValue;
		}

		@Override
		public String toString()
		{
			return stringValue + intValue + booleanValue;
		}

	}

}
