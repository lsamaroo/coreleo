package com.coreleo.interfaces;

/**
 * 
 * @author Leon Samaroo
 * 
 * Generic interface for objects which support
 * basic create, read, update, delete 
 * behavior.
 *
 */
public interface CrudSupport {

	public Object create(Object obj);
	
	public Object read(Object obj);

	public Object update(Object obj);

	public Object remove(Object obj);
}
