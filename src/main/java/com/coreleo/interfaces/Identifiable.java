package com.coreleo.interfaces;

/**
 * 
 * Used to provide domain objects with a common interface for getting the name and id.
 *
 */
public interface Identifiable
{
	public Object getIdentity();

	public Object getIdentityName();
}
