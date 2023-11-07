/**
 * 
 */
package com.coreleo.util.monitor;

import java.util.*;

/**
 * @author Leon Samaroo
 * 
 */
@SuppressWarnings({"rawtypes","unchecked"})
public abstract class ObjectMonitorManager {
	private Timer timer;
	private Map timerEntries;


	public ObjectMonitorManager() {
		// Create daemon timer thread.
		timer = new Timer(true);
		timerEntries = new HashMap();
	}


	/**
	 * Add a monitored file with a ObjectChangeListener.
	 * 
	 * @param listener
	 *            listener to notify when the object changed.
	 * @param object -
	 *            the item to monitor.
	 * @param period
	 *            polling period in milliseconds.
	 */
	public final void addObjectChangeListener(ObjectChangeListener listener, Object theMonitoredObject, long periodInMillis ) throws ObjectNotFoundException {
		removeObjectChangeListener(listener, theMonitoredObject);
		ObjectMonitor task = getMonitor(listener, theMonitoredObject);
		timerEntries.put("" + theMonitoredObject + listener.hashCode(), task);
		timer.schedule(task, periodInMillis, periodInMillis);
	}


	/**
	 * Remove the listener from the notification list.
	 * 
	 * @param listener
	 *            the listener to be removed.
	 */
	public final void removeObjectChangeListener(ObjectChangeListener listener, Object theMonitoredObject) {
		ObjectMonitor task = (ObjectMonitor) timerEntries.remove("" + theMonitoredObject + listener.hashCode());
		if (task != null) {
			task.cancel();
		}
	}


	/**
	 * 
	 * Returns an ObjectMonitorTask for the item being monitored. The
	 * ObjectMonitorTask defines how this item should be monitored for changes.
	 * 
	 */
	protected abstract ObjectMonitor getMonitor(ObjectChangeListener listener, Object theMonitoredObject) throws ObjectNotFoundException;

}