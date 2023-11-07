/**
 * 
 */
package com.coreleo.util.thread;

/**
 * @author Leon Samaroo
 * 
 */
public class WorkerThread extends Thread {

	private ThreadPool threadPoolOwner;
	private boolean stop = false;
	private Runnable task;


	public WorkerThread(ThreadPool owner) {
		super();
		this.threadPoolOwner = owner;
	}


	public ThreadPool getOwner() {
		return threadPoolOwner;
	}


	/*
	 * Interupt the WorkerThread
	 */
	public void kill() {
		stop = true;
		interrupt();
	}


	public boolean isStopped() {
		return stop;
	}


	public void runTask(Runnable runnable) {
		task = runnable;
		synchronized (this) {
			notifyAll();
		}
	}


	public void run() {
		while (!stop) {
			while (task == null) {
				try {
					synchronized (this) {
						wait();
					}
				}
				catch (InterruptedException ex) {
					if (stop) {
						System.out.println("WorkerThread: interrupted");
						return;
					}
				}
			}
			try {
				task.run();
			}
			catch (Throwable t) {
				t.printStackTrace();
			}

			task = null;
			threadPoolOwner.returnWorker(this);

		}
		System.out.println("WorkerThread: quiting");

	}

}
