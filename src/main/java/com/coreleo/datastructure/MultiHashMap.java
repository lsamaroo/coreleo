package com.coreleo.datastructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @deprecated - use apache's MultiHashMap instead
 * @author Leon Samaroo
 *
 *         An HashMap which allows mapping multiple values to a single key.
 *
 */
@Deprecated
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MultiHashMap extends HashMap implements Serializable {

	private static final long serialVersionUID = 1L;

	public boolean containsValue(final Object key, final Object value) {
		final List list = (List) super.get(key);
		if (list != null) {
			return list.contains(value);
		}
		else {
			return false;
		}
	}

	public List getCollection(final Object key) {
		return (List) super.get(key);
	}

	public Iterator iterator(final Object key) {
		final List list = (List) super.get(key);
		if (list != null) {
			return list.iterator();
		}
		else {
			return new ArrayList().iterator();
		}
	}

	public boolean putAll(final Object key, final Collection values) {
		if (values == null) {
			return false;
		}

		final List list = (List) super.get(key);
		if (list != null) {
			return list.addAll(values);
		}
		else {
			super.put(key, list);
			return true;
		}
	}

	public int size(final Object key) {
		final List list = (List) super.get(key);
		if (list != null) {
			return list.size();
		}
		else {
			return 0;
		}
	}

	public int totalSize() {
		int size = 0;

		for (final Iterator i = super.values().iterator(); i.hasNext();) {
			final List list = (List) i.next();
			size = size + list.size();
		}

		return size;
	}

	public Object remove(final Object key, final Object item) {
		final List list = (List) super.get(key);
		if (list != null) {
			final boolean containsItem = list.remove(item);
			return containsItem ? item : null;
		}
		else {
			return null;
		}
	}

	@Override
	public Object put(final Object key, final Object value) {
		List list = (List) super.get(key);
		if (list == null) {
			list = new ArrayList();
		}

		list.add(value);
		return super.put(key, list);
	}

	@Override
	public boolean containsValue(final Object value) {
		for (final Iterator i = super.values().iterator(); i.hasNext();) {
			final List list = (List) i.next();
			if (list.contains(value)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public List values() {
		final List all = new ArrayList();
		for (final Iterator i = super.values().iterator(); i.hasNext();) {
			final List list = (List) i.next();
			all.addAll(list);
		}

		return all;
	}

}
