package com.coreleo.util.pool;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.coreleo.util.LogUtil;
import com.coreleo.util.MapUtil;

/**
 *
 * Object threadPool design is generic enough to handle storage, tracking, and
 * expiration times, but instantiation, validation, and destruction of specific
 * object types must be handled by sub-classing.
 *
 */
public abstract class ObjectPool<T> extends AbstractPool<T> {
	private final ExecutorService threadPool = Executors.newSingleThreadExecutor();

	private final Map<T, MetaData> locked;
	private final Map<T, MetaData> unlocked;
	private final int minObjectsInPool;
	private final int maxObjectsInPool;
	/**
	 * Specifies the interval in milliseconds after which an idle connection is
	 * discarded. A value of zero means allow to idle indefinitely.
	 */
	private final long objectIdleTimeOut;

	/**
	 * Specifies the length of life in milliseconds for a connection, 0 is alive
	 * forever.
	 */
	private final long objectLife;

	private long lastCheckOutTime = System.currentTimeMillis();
	private int numberOfObjectsLocked = 0;
	private int numberOfObjectsUnLocked = 0;

	private final long reapTime;

	protected ObjectPool(final String name, final long objectIdleTimeOut, final long objectLife, final long reapTime,
	        final int minObjectsInPool, final int maxObjectsInPool) {
		super(name);
		this.locked = new Hashtable<>();
		this.unlocked = new Hashtable<>();
		this.objectIdleTimeOut = objectIdleTimeOut;
		this.objectLife = objectLife;
		this.minObjectsInPool = minObjectsInPool;
		this.maxObjectsInPool = maxObjectsInPool;
		this.reapTime = reapTime;

		LogUtil.trace(this, "ObjectPool:constructor " + this.toString());
	}

	protected ObjectPool(final long objectIdleTimeOut, final long objectLife, final long reapTime,
	        final int minObjectsInPool, final int maxObjectsInPool) {
		super();
		this.locked = new Hashtable<>();
		this.unlocked = new Hashtable<>();
		this.objectIdleTimeOut = objectIdleTimeOut;
		this.objectLife = objectLife;
		this.minObjectsInPool = minObjectsInPool;
		this.maxObjectsInPool = maxObjectsInPool;
		this.reapTime = reapTime;

		LogUtil.trace(this, "ObjectPool:constructor " + this.toString());
	}

	public void startObjectReaper() {
		LogUtil.trace(this, "ObjectPool:startObjectReaper");
		threadPool.execute(new ObjectReaper(this, reapTime));
	}

	public synchronized long getLastCheckOutTime() {
		return lastCheckOutTime;
	}

	/**
	 *
	 * @return the object idle time out
	 * @deprecated use getIdleTimeOut instead
	 */
	@Deprecated
	public long getExpire() {
		return objectIdleTimeOut;
	}

	public long getObjectIdleTimeOut() {
		return objectIdleTimeOut;
	}

	public long getObjectLife() {
		return objectLife;
	}

	/**
	 *
	 * Returns the interval, in seconds, between runs of the
	 * <code>ObjectReaper </code>.
	 *
	 * @deprecated
	 *
	 */
	@Deprecated
	public long getReapTime() {
		return reapTime;
	}

	@Override
	public synchronized int getNumberOfObjectsLocked() {
		return this.numberOfObjectsLocked;
	}

	@Override
	public synchronized int getNumberOfObjectsUnLocked() {
		return this.numberOfObjectsUnLocked;
	}

	public int getMinimumObjectsInPool() {
		return this.minObjectsInPool;
	}

	public int getMaximumObjectsInPool() {
		return this.maxObjectsInPool;
	}

	@Override
	public String toString() {
		return "id=" + super.getId() + " name=" + super.getName() + " objectIdleTimeOut=" + objectIdleTimeOut
		        + " objectLife=" + objectLife;
	}

	protected synchronized void initializeObjects(final int numberOfObjectsInPool) throws Exception {
		LogUtil.trace(this, "ObjectPool:initializeObjects");
		for (int i = 0; i < numberOfObjectsInPool; i++) {
			unlocked.put(create(), new MetaData());
			LogUtil.debug(this, "ObjectPool:initializeObjects - " + i + 1);
		}
		this.numberOfObjectsUnLocked = unlocked.size();
	}

	@Override
	public synchronized T checkOut() throws Exception {
		LogUtil.trace(this, "ObjectPool:checkOut - name=" + super.getName());

		lastCheckOutTime = System.currentTimeMillis();
		T object;
		MetaData metaData;
		Map.Entry<T, MetaData> entry;

		if (unlocked.size() > 0) {
			try {
				for (final Iterator<Map.Entry<T, MetaData>> i = unlocked.entrySet().iterator(); i.hasNext();) {
					entry = i.next();
					object = entry.getKey();
					if (validate(object)) {
						metaData = entry.getValue();
						i.remove();
						locked.put(object, metaData);
						LogUtil.trace(this, "ObjectPool:checkOut - name=" + super.getName() + " obj=" + object
						        + " metaData=" + metaData);
						return (object);
					}
					else {
						i.remove();
						expire(object);
						object = null;
						metaData = null;
					}
				}
			}
			catch (final Exception e) {
				LogUtil.warn(this, "ObjectPool:checkOut - name=" + super.getName()
				        + " error retrieving object from unlocked threadPool", e);
			}
		}

		// If we reached here then we either have a empty threadPool or invalid
		// (via validate method) objects.
		object = create();
		metaData = new MetaData();
		locked.put(object, metaData);
		this.numberOfObjectsLocked = locked.size();
		this.numberOfObjectsUnLocked = unlocked.size();

		LogUtil.trace(this,
		        "ObjectPool:checkOut - name=" + super.getName() + " obj=" + object + " metaData=" + metaData);
		return (object);
	}

	@Override
	public synchronized void checkIn(final T obj) {
		LogUtil.trace(this, "ObjectPool:checkIn - name=" + super.getName() + " obj=" + obj);

		if (obj != null) {
			LogUtil.trace(this, "ObjectPool:checkIn - name=" + super.getName() + " checking in connection");

			final int totalObjectsInPool = locked.size() + unlocked.size();
			MetaData metaData = locked.remove(obj);

			if (metaData != null) {
				if (totalObjectsInPool <= maxObjectsInPool || maxObjectsInPool == 0) {
					metaData = new MetaData(metaData.getCreated(), System.currentTimeMillis());
					unlocked.put(obj, metaData);
				}
				else {
					expire(obj);
				}
			}
			else {
				LogUtil.error(this, "ObjectPool:checkIn - name=" + super.getName()
				        + " unable to checkin, object does not belong to this threadPool");
			}
		}
		else {
			LogUtil.error(this, "ObjectPool:checkIn - name=" + super.getName() + " unable to checkin, object is null");
		}

		this.numberOfObjectsLocked = locked.size();
		this.numberOfObjectsUnLocked = unlocked.size();
		LogUtil.trace(this,
		        "ObjectPool:checkIn - name=" + super.getName() + " numberOfObjectsLocked=" + numberOfObjectsLocked
		                + " numberOfObjectsUnLocked= " + numberOfObjectsUnLocked + " totalObjectsInPool="
		                + (numberOfObjectsLocked + numberOfObjectsUnLocked));
	}

	@Override
	protected synchronized void cleanUp() {
		LogUtil.trace(this, "ObjectPool:cleanUp - name=" + super.getName());
		final long now = System.currentTimeMillis();

		T object;
		MetaData metaData;
		Map.Entry<T, MetaData> entry;

		try {
			for (final Iterator<Map.Entry<T, MetaData>> i = unlocked.entrySet().iterator(); i.hasNext();) {
				if ((locked.size() + unlocked.size()) <= minObjectsInPool) {
					LogUtil.trace(this, "ObjectPool:cleanUp - name=" + super.getName()
					        + " minimum objects in threadPool reached, finished clean up");
					break;
				}

				entry = i.next();
				object = entry.getKey();
				metaData = entry.getValue();
				final long timeIdleInPool = (now - (metaData != null ? metaData.getAccessed() : 0));
				final long timePassedSinceCreated = (now - (metaData != null ? metaData.getCreated() : 0));
				final boolean hasIdled = (objectIdleTimeOut != 0 && timeIdleInPool > objectIdleTimeOut);
				final boolean hasGrownOld = (objectLife != 0 && timePassedSinceCreated > objectLife);
				if (hasIdled || hasGrownOld) {
					LogUtil.debug(this, "ObjectPool:cleanUp - name=" + super.getName() + " expiring object=" + object
					        + " metaData=" + metaData + " timeIdleInPool=" + timeIdleInPool + " hasIdled=" + hasIdled
					        + " timePassedSinceCreated=" + timePassedSinceCreated + " hasGrownOld=" + hasGrownOld);
					i.remove();
					expire(object);
				}
			}
		}
		catch (final Exception e) {
			LogUtil.warn(this, "ObjectPool:cleanUp - name=" + super.getName() + " error cleaning up threadPool", e);
		}

		this.numberOfObjectsLocked = locked.size();
		this.numberOfObjectsUnLocked = unlocked.size();
	}

	/**
	 * Closes all available connections.
	 */
	protected synchronized void release() {
		LogUtil.trace(this, "ObjectPool:release");
		T object;

		for (final Iterator<Map.Entry<T, MetaData>> i = unlocked.entrySet().iterator(); i.hasNext();) {
			object = i.next().getKey();
			i.remove();
			expire(object);
		}

		this.numberOfObjectsUnLocked = 0;
	}

	public synchronized void close() {
		LogUtil.trace(this, "ObjectPool:close");
		release();

		MapUtil.clear(unlocked);
		MapUtil.clear(locked);
		threadPool.shutdownNow();
	}

	@Override
	protected void finalize() throws Throwable {
		LogUtil.trace(this, "ObjectPool:finalize");
		super.finalize();
		close();
	}

	private final static class MetaData {
		private final long created;
		private final long accessed;

		public MetaData() {
			final long now = System.currentTimeMillis();
			created = now;
			accessed = now;
		}

		public MetaData(final long created, final long accesssed) {
			this.created = created;
			this.accessed = accesssed;
		}

		public long getCreated() {
			return created;
		}

		public long getAccessed() {
			return accessed;
		}

		@Override
		public String toString() {
			return "ObjectMetaData - created=" + created + " accessed=" + accessed;
		}

	}

}