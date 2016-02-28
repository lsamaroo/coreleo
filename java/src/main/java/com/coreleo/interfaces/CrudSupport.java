package com.coreleo.interfaces;


/**
 * 
 * @author Leon Samaroo
 *         Generic interface for objects which support basic create, read, update, delete behavior.
 * 
 */
public interface CrudSupport
{

	Object create(Object... params);

	Object read(Object... params);

	Object update(Object... params);

	Object remove(Object... params);
}
