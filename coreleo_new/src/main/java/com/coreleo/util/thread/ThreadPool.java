/**
 * 
 */
package com.coreleo.util.thread;

import com.coreleo.util.*;
import com.coreleo.util.pool.ObjectPool;

/**
 * @author Leon Samaroo
 * 
 */
public class ThreadPool extends ObjectPool {
    private final static int DEFAULT_EXPIRE_TIME = (60 * 1000 * 30);
    private final static int DEFAULT_REAP_TIME = (60 * 1000 * 3);
    private final static int DEFAULT_MAX_THREAD = 50;
    private final static int DEFAULT_MIN_THREAD = 0;
    private int maxThread;
    private int threadCounter = 0;


    public ThreadPool() {
        super("Thread Pool", DEFAULT_EXPIRE_TIME, 0, DEFAULT_REAP_TIME, DEFAULT_MIN_THREAD, DEFAULT_MAX_THREAD);
        this.maxThread = DEFAULT_MAX_THREAD;
    }


    protected Object create() throws Exception {
        WorkerThread workerThread = new WorkerThread(this);
        workerThread.start();
        return workerThread;
    }


    /**
     * Validates the worker thread. There are 2 situations when this method
     * returns false, first if the argument is null, and the second is if the
     * given thread is stopped.
     * 
     */
    protected boolean validate(Object obj) {
        WorkerThread worker = (WorkerThread) obj;
        if (worker == null) {
            return false;
        }

        if (worker.isStopped()) {
            return false;
        }

        return true;
    }


    protected void expire(Object obj) {
        LogUtil.trace(this, "ThreadPool:expire - obj=" + obj);
        WorkerThread worker = (WorkerThread) obj;
        if (worker != null) {
            worker.kill();
            worker = null;
        }
    }


    protected void returnWorker(WorkerThread worker) {
        LogUtil.trace(this, "ThreadPool:returnWorker - WorkerThread=" + worker);
        synchronized (this) {
            LogUtil.trace(this, "ThreadPool:returnWorker - synchronized block");
            threadCounter--;
            super.checkIn(worker);
            this.notifyAll();
        }
    }


    public WorkerThread borrowWorker() throws ThreadPoolException {
        LogUtil.trace(this, "ThreadPool:borrowWorker");
        try {
            synchronized (this) {
                LogUtil.trace(this, "ThreadPool:borrowWorker - synchronized block");
                while (threadCounter >= maxThread) {
                    LogUtil.debug(this, "ThreadPool:borrowWorker - Waiting for free Thread, count=" + threadCounter + " maxThread=" + maxThread);
                    this.wait();
                }

                threadCounter++;
                return (WorkerThread) super.checkOut();
            }
        }
        catch (Exception ex) {
            throw new ThreadPoolException(ex);
        }

    }

}
