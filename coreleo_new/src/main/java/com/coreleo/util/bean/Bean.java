/**
 * 
 */
package com.coreleo.util.bean;

import java.util.Map;

/**
 * @author Leon Samaroo
 *
 */
public interface Bean {

	/**
	 * 
	 * This method will initialize the bean with the given map.
	 */
	public void init(Map map);

	
    /**
     * Resets the bean to its default state.
     */
	public void reset();



}