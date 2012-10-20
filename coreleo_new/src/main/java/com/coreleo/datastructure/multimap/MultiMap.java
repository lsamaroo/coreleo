package com.coreleo.datastructure.multimap;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Leon Samaroo
 * 
 *         An interface of a map which allows mapping multiple values to a single key.
 * 
 */
public interface MultiMap extends Map
{

	public boolean containsValue(Object key, Object value);

	public Object remove(Object key, Object value);

	public List<Object> getCollection(Object key);

	public Iterator<Object> iterator(Object key);

	public boolean putAll(Object key, Collection<Object> values);

	public int size(Object key);

	/** @return - the total size of the map by counting all the values */
	public int totalSize();

	/** @return - a List containing all the values in the map */
	@Override
	public List<Object> values();

}
