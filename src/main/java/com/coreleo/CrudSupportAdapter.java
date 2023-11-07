package com.coreleo;

/**
 * 
 * @author Leon Samaroo
 * 
 * An abstract adapter class for receiving CRUD support (create, read, update, delete) . 
 * The methods in this class are empty. 
 * This class exists as convenience for creating CRUD supportable objects. 
 *
 */
public abstract class CrudSupportAdapter implements com.coreleo.interfaces.CrudSupport {


	public Object create(Object obj) {
		return null;
	}


	public Object read(Object obj) {
		return null;
	}


	public Object remove(Object obj) {
		return null;
	}


	public Object update(Object obj) {
		return null;
	}

}
