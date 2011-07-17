package com.coreleo.util.closure;

import java.util.*;

public abstract class StatefulBlock implements Block {
	/**
	 * Map object used to store stateful information when this Block 
	 * object is reused.
	 * 
	 * This class is not thread safe.
	 * 
	 */
	protected final Map state = new HashMap();
 
	public Map getState() {
		return state;
	}

}
