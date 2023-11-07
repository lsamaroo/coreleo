package com.coreleo.util.monitor.file;

import java.io.File;
import java.net.URL;

import com.coreleo.util.LogUtil;
import com.coreleo.util.NumberUtil;
import com.coreleo.util.monitor.*;

/**
 * @author Leon Samaroo
 * 
 */
public class FileMonitorManager extends ObjectMonitorManager {

	public FileMonitorManager() {
		super();
	}


	// -------------------------------------------------------------------------------------------
	// Implementation of abstract class ObjectMonitorManager
	// -------------------------------------------------------------------------------------------

	protected ObjectMonitor getMonitor(ObjectChangeListener listener, Object item) throws ObjectNotFoundException {
		return new FileMonitor(listener, (File) item);
	}

	// -------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------

	static class FileMonitor extends ObjectMonitor {
		private Long lastModified;
		private File file;


		public FileMonitor(ObjectChangeListener listener, File thefile) throws ObjectNotFoundException {
			super(listener, thefile);
			this.lastModified = null;
			this.file = thefile;

			if (!file.exists()) {
				// then check the classpath
				URL fileURL = listener.getClass().getClassLoader().getResource(file.getName());
				if (fileURL != null) {
					file = new File(fileURL.getFile());
				}
				else {
					throw new ObjectNotFoundException("File Not Found: " + file);
				}
			}
			this.lastModified = (Long) getLastChangedCriteria();
		}


		protected Object getLastChangedCriteria() {
			return NumberUtil.toLongObject(file.lastModified());
		}


		protected boolean hasChanged() {
			Long lastModified = (Long) getLastChangedCriteria();
			if (!lastModified.equals(this.lastModified)) {
				this.lastModified = lastModified;
				return true;
			}
			return false;
		}

	}


	public static void main(String[] args) {
		final String fileName = "database.properties";
		System.out.println("Testing FileMonitorManager using " + fileName);
		try {
			new FileMonitorManager().addObjectChangeListener(new ObjectChangeListener() {
				public void objectChanged(Object obj) {
					System.out.println("THE OBJECT CHANGED " + obj);
				}
			}, new java.io.File(fileName), 5000);
		}
		catch (ObjectNotFoundException onfe) {
			LogUtil.error(onfe);
		}

		System.out.println("Make a change to " + fileName + " to see the event fired.");

		while (true) { /* run forever for the test */
		}
	}

}
