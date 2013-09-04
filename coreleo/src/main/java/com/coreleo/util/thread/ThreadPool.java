/**
 * 
 */
package com.coreleo.util.thread;

import com.coreleo.util.LogUtil;
import com.coreleo.util.pool.ObjectPool;

/**
 * @author Leon Samaroo
 * 
 */
public class ThreadPool extends ObjectPool<WorkerThread>
{
	private final static int DEFAULT_EXPIRE_TIME = (60 * 1000 * 30);
	private final static int DEFAULT_REAP_TIME = (60 * 1000 * 3);
	private final static int DEFAULT_MAX_THREAD = 50;
	private final static int DEFAULT_MIN_THREAD = 0;
	private final int maxThread;
	private int threadCounter = 0;

	public ThreadPool()
	{
		super("Thread Pool", DEFAULT_EXPIRE_TIME, 0, DEFAULT_REAP_TIME, DEFAULT_MIN_THREAD, DEFAULT_MAX_THREAD);
		this.maxThread = DEFAULT_MAX_THREAD;
	}

	@Override
	protected WorkerThread create() throws Exception
	{
		final WorkerThread workerThread = new WorkerThread(this);
		workerThread.start();
		return workerThread;
	}

	/**
	 * Validates the worker thread. There are 2 situations when this method returns false, first if the argument is null, and the
	 * second is if the given thread is stopped.
	 * 
	 */
	@Override
	protected boolean validate(WorkerThread obj)
	{
		final WorkerThread worker = obj;
		if (worker == null)
		{
			return false;
		}

		if (worker.isStopped())
		{
			return false;
		}

		return true;
	}

	@Override
	protected void expire(WorkerThread obj)
	{
		LogUtil.trace(this, "ThreadPool:expire - obj=" + obj);
		WorkerThread worker = obj;
		if (worker != null)
		{
			worker.kill();
			worker = null;
		}
	}

	protected void returnWorker(WorkerThread worker)
	{
		LogUtil.trace(this, "ThreadPool:returnWorker - WorkerThread=" + worker);
		synchronized (this)
		{
			LogUtil.trace(this, "ThreadPool:returnWorker - synchronized block");
			threadCounter--;
			super.checkIn(worker);
			this.notifyAll();
		}
	}

	protected WorkerThread borrowWorker() throws ThreadPoolException
	{
		LogUtil.trace(this, "ThreadPool:borrowWorker");
		try
		{
			synchronized (this)
			{
				LogUtil.trace(this, "ThreadPool:borrowWorker - synchronized block");
				while (threadCounter >= maxThread)
				{
					LogUtil.debug(this, "ThreadPool:borrowWorker - Waiting for free Thread, count=" + threadCounter + " maxThread=" + maxThread);
					this.wait();
				}

				threadCounter++;
				return super.checkOut();
			}
		}
		catch (final Exception ex)
		{
			throw new ThreadPoolException(ex);
		}

	}

	public void runTask(Runnable runnable) throws ThreadPoolException
	{
		borrowWorker().runTask(runnable);
	}

	public static void main(String args[])
	{
		final ThreadPool pool = new ThreadPool();
		final Runnable run = new Runnable()
		{
			@Override
			public void run()
			{
				for (int i = 0; i < 10; i++)
				{
					System.out.println(Thread.currentThread().getId() + " " + i);
				}

			}
		};

		final Runnable run2 = new Runnable()
		{
			@Override
			public void run()
			{
				for (int i = 0; i < 10; i++)
				{
					System.out.println(Thread.currentThread().getId() + " " + i);
				}

			}
		};

		try
		{
			pool.runTask(run);
			pool.runTask(run2);
		}
		catch (final ThreadPoolException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
