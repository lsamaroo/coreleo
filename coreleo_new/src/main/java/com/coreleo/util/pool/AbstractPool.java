package com.coreleo.util.pool;

public abstract class AbstractPool<T>
{
	private final long id = System.currentTimeMillis();
	private final String name;

	public AbstractPool()
	{
		super();
		this.name = "Pool" + id;
	}

	public AbstractPool(String name)
	{
		super();
		this.name = name;
	}

	public long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public abstract T checkOut() throws Exception;

	/**
	 * 
	 * Returns the object to the pool.
	 * 
	 * @param obj
	 *            - the object to return to the pool.
	 */
	public abstract void checkIn(T obj);

	protected abstract void cleanUp();

	/**
	 * 
	 * Sub-classes are responsible for implementing this method. Used by checkOut to validate the object in the pool.
	 * 
	 * Used by checkOut to create new Objects if none are available from the pool.
	 * 
	 * @return - A new object.
	 * @throws Exception
	 *             - Propagates all Exceptions to caller.
	 */
	protected abstract T create() throws Exception;

	/**
	 * 
	 * Sub-classes are responsible for implementing this method. Used by checkOut to validate the object in the pool.
	 * 
	 * @param obj
	 *            - the object to be validated.
	 * 
	 * @return true if valid, false otherwise.
	 */
	protected abstract boolean validate(T obj);

	/**
	 * Sub-classes are responsible for implementing this method. Responsible for safely freeing resources used by the object
	 * before it is claimed by garbage collection.
	 * 
	 * For example, closing a network or file connection.
	 * 
	 * @param obj
	 *            - the object to expire.
	 */
	protected abstract void expire(T obj);

	public abstract int getNumberOfObjectsLocked();

	public abstract int getNumberOfObjectsUnLocked();

}