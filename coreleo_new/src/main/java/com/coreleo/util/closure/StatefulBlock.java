package com.coreleo.util.closure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class StatefulBlock<K, V> implements Block
{
	/**
	 * Map object used to store stateful information when this Block object is reused.
	 * 
	 * The state object is a concurrent hash map so it should be safe for access by multiple threads.
	 */
	protected final Map<K, V> state = new ConcurrentHashMap<K, V>();

	public Map<K, V> getState()
	{
		return state;
	}

}
