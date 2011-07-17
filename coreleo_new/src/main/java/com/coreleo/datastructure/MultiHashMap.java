package com.coreleo.datastructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MultiHashMap extends HashMap implements MultiMap, Serializable {
	
	private static final long serialVersionUID = 1L;


	public boolean containsValue(Object key, Object value) {
		List list = (List) super.get(key);
		if (list != null) {
			return list.contains(value);
		}
		else{
			return false;
		}
	}



	public List getCollection(Object key) {
		return (List) super.get(key);
	}



	public Iterator iterator(Object key) {
		List list = (List) super.get(key);
		if (list != null) {
			return list.iterator();
		}
		else{
			return new ArrayList().iterator();
		}
	}



	public boolean putAll(Object key, Collection values) {
		if( values == null ){
			return false;
		}
		
		List list = (List) super.get(key);
		if (list != null) {
			return list.addAll(values);
		}
		else{
			super.put(key, list);
			return true;
		}
	}



	public int size(Object key) {
		List list = (List) super.get(key);
		if (list != null) {
			return list.size();
		}
		else{
			return 0;
		}
	}



	public int totalSize() {
		int size = 0;
		
		for (Iterator i = super.values().iterator(); i.hasNext();) {
			List list = (List) i.next();
			size = size + list.size();
		}
		
		return size;
	}



	public Object remove(Object key, Object item) {
		List list = (List) super.get(key);
		if (list != null) {
			boolean containsItem = list.remove(item);
			return containsItem ? item : null;
		}
		else{
			return null;
		}
	}
	
	

	public Object put(Object key, Object value) {
		List list = (List) super.get(key);
		if (list == null) {
			list = new ArrayList();
		}
		
		list.add(value);
		return super.put(key, list);
	}


	
	public boolean containsValue(Object value) {
		for (Iterator i = super.values().iterator(); i.hasNext();) {
			List list = (List) i.next();
			if( list.contains(value) ){
				return true;
			}
		}

		return false;
	}


	public List values() {
		List all = new ArrayList();
		for (Iterator i = super.values().iterator(); i.hasNext();) {
			List list = (List) i.next();
			all.addAll(list);
		}
		
		return all;
	}
	
	
	

}
