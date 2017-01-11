/**
 *
 */
package com.coreleo.util.pool;

import com.coreleo.util.LogUtil;

/**
 *
 * Cleanup thread is a thread which calls the cleanUp method of ObjectPool every
 * <code>sleepTime</code> seconds.
 *
 */
public class ObjectReaper implements Runnable {
	private final static long DEFAULT_SLEEP_INTERVAL = 30 * 60 * 1000; // 30
	                                                                   // minutes
	private final AbstractPool<?> pool;
	private final long sleepInterval;

	public ObjectReaper(final AbstractPool<?> pool, final long sleepInterval) {
		super();
		this.pool = pool;

		if (sleepInterval <= 0) {
			LogUtil.warn(this, "ObjectReaper:constructor - Invalid sleepInterval of " + sleepInterval
			        + "ms defaulting to " + DEFAULT_SLEEP_INTERVAL + "ms");
			this.sleepInterval = DEFAULT_SLEEP_INTERVAL;
		}
		else {
			this.sleepInterval = sleepInterval;
		}

		LogUtil.trace(this,
		        "ObjectReaper:constructor - pool=" + pool.getName() + " sleepInterval=" + sleepInterval + "ms");
	}

	public long getSleepInterval() {
		return sleepInterval;
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				LogUtil.trace(this, "ObjectReaper:run - pool=" + pool.getName() + " Thread.sleep for sleepInterval="
				        + sleepInterval);
				Thread.sleep(sleepInterval);
				pool.cleanUp();
			}
		}
		catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		LogUtil.info(this, "Stopping thread.");
	}

}
