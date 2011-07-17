package com.coreleo.datastructure;


import java.util.*;

/**
 * 
 * @author Leon Samaroo
 * 
 * An interface representing map that allows mapping  
 * multiple values to a single key.
 *
 */
public interface MultiMap extends Map {

	public boolean containsValue( Object key, Object value );

	public Object remove( Object key, Object item );
	
	public List getCollection( Object key );
	
	public Iterator iterator( Object key );
	
	public boolean putAll( Object key, Collection values );
	
	public int size( Object key );
	
	/** @return - the total size of the map by counting all the values */
	public int totalSize();
	
	/** @return - a List containing all the values in the map */
	public List values();
	
}
