package com.coreleo.datastructure.multimap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Leon Samaroo
 * 
 *         An HashMap which allows mapping multiple values to a single key.
 * 
 */
public class MultiHashMap extends HashMap implements MultiMap, Serializable
{

	private static final long serialVersionUID = 1L;

	@Override
	public boolean containsValue(Object key, Object value)
	{
		final List list = (List) super.get(key);
		if (list != null)
		{
			return list.contains(value);
		}
		else
		{
			return false;
		}
	}

	@Override
	public List getCollection(Object key)
	{
		return (List) super.get(key);
	}

	@Override
	public Iterator iterator(Object key)
	{
		final List list = (List) super.get(key);
		if (list != null)
		{
			return list.iterator();
		}
		else
		{
			return new ArrayList().iterator();
		}
	}

	@Override
	public boolean putAll(Object key, Collection values)
	{
		if (values == null)
		{
			return false;
		}

		final List list = (List) super.get(key);
		if (list != null)
		{
			return list.addAll(values);
		}
		else
		{
			super.put(key, list);
			return true;
		}
	}

	@Override
	public int size(Object key)
	{
		final List list = (List) super.get(key);
		if (list != null)
		{
			return list.size();
		}
		else
		{
			return 0;
		}
	}

	@Override
	public int totalSize()
	{
		int size = 0;

		for (final Iterator i = super.values().iterator(); i.hasNext();)
		{
			final List list = (List) i.next();
			size = size + list.size();
		}

		return size;
	}

	@Override
	public Object remove(Object key, Object item)
	{
		final List list = (List) super.get(key);
		if (list != null)
		{
			final boolean containsItem = list.remove(item);
			return containsItem ? item : null;
		}
		else
		{
			return null;
		}
	}

	@Override
	public Object put(Object key, Object value)
	{
		List list = (List) super.get(key);
		if (list == null)
		{
			list = new ArrayList();
		}

		list.add(value);
		return super.put(key, list);
	}

	@Override
	public boolean containsValue(Object value)
	{
		for (final Iterator i = super.values().iterator(); i.hasNext();)
		{
			final List list = (List) i.next();
			if (list.contains(value))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public List values()
	{
		final List all = new ArrayList();
		for (final Iterator i = super.values().iterator(); i.hasNext();)
		{
			final List list = (List) i.next();
			all.addAll(list);
		}

		return all;
	}

}
