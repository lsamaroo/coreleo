package com.coreleo.util.monitor;

import java.util.TimerTask;

/**
 * @author Leon Samaroo
 * 
 */
public abstract class ObjectMonitor extends TimerTask {
	protected ObjectChangeListener listener;
	protected Object monitoredItem;


	public ObjectMonitor(ObjectChangeListener listener, Object theMonitoredObject) {
		this.listener = listener;
		this.monitoredItem = theMonitoredObject;
	}


	public final void run() {
		if (hasChanged()) {
			this.listener.objectChanged(monitoredItem);
		}
	}


	/**
	 * Returns the criteria used when comparing the states of an object to see
	 * if it has changed. For example when monitoring files, a criteria may be
	 * the last modified date.
	 * 
	 * @return the critera used for comparing the object state.
	 */
	protected abstract Object getLastChangedCriteria();


	/**
	 * 
	 * @return true if the object being monitored has changed its state from the
	 *         last time it was checked, false otherwise.
	 * 
	 */
	protected abstract boolean hasChanged();
}