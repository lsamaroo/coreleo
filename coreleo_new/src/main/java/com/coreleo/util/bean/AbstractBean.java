package com.coreleo.util.bean;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 * @deprecated - this implementation of the Bean interface
 * serves very little purpose. Programmer should implement the 
 * Bean interface directly.
 *
 */
public abstract class AbstractBean implements Comparable, Bean {
	private long beanId;


	public AbstractBean() {
		super();
		this.beanId = System.currentTimeMillis();
		reset();
	}



	public long getBeanId() {
		return this.beanId;
	}
	
	

	public void reInit( Map map ) {
		reset();
		init( map );
	}

	
	/**
	 * Overwrite to initialize the bean.
	 * This method should not be called directly, instead callers
	 * should call the public method reInit( map ).
	 * 
	 */
	public abstract void init(Map map);


	/** Resets the bean to its default state. */
	public abstract void reset();



	public ErrorMessage getAnyErrorMessage() {
		return null;
	}


	public ErrorMessage validate(ResourceBundle bundle) {
		return null;
	}


	/** Do class cleanup here. */
	protected abstract void release();


	public int compareTo(Object obj) {
		AbstractBean anotherBean = (AbstractBean) obj;
		return (this.beanId - anotherBean.getBeanId()) == 0 ? 0 : -1;
	}


	protected void finalize() throws Throwable {
		release();
		super.finalize();
	}

}
