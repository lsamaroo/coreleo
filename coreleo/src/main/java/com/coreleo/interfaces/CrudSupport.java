package com.coreleo.interfaces;


/**
 * 
 * @author Leon Samaroo
 * 
 *         Generic interface for objects which support basic create, read, update, delete behavior.
 * 
 */
public interface CrudSupport
{

	public Object create(Object... params);

	public Object read(Object... params);

	public Object update(Object... params);

	public Object remove(Object... params);
}
