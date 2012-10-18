package com.coreleo.util.filecrawler;

import java.io.File;
import java.io.FileFilter;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Leon Samaroo
 * 
 *         A file crawler which concurrently scans the specified directory AND concurrently perform actions on found files.
 * 
 * 
 */

public final class FileCrawler
{
	private static final File POISON_PILL = new File("");

	private FileCrawler()
	{
		super();
	}

	/**
	 * 
	 * This method uses the passed in ConcurrentLinkedQueue to store the file list. This method returns when all the threads have
	 * completed their scan.
	 * 
	 * @param dir
	 *            - the root directory to start crawling
	 * @param filter
	 *            - a file filter to filter files while crawling or null
	 * @param fileQueue
	 *            - a ConcurrentLinkedQueue to store the file list
	 * @param numThreads
	 *            - number of crawler threads to create
	 * @throws InterruptedException
	 */

	public static final void crawl(File dir, FileFilter filter, ConcurrentLinkedQueue<File> fileQueue, int numThreads) throws InterruptedException
	{
		final ExecutorService executor = createAndStartProducers(dir, filter, fileQueue, numThreads, 0);
		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}

	/**
	 * 
	 * This method allows you to concurrently scan the given directory AND concurrently perform an action on the files as they are
	 * found.
	 * 
	 * @param dir
	 *            - the root directory to start crawling
	 * @param filter
	 *            - a file filter to filter files while crawling or null
	 * @param numCrawlThreads
	 *            - number of crawler threads to create
	 * @param throttleCrawlThreads
	 *            - the amount of time to throttle the crawler threads in millis. Zero for no throttling.
	 * @param action
	 *            - The action to perform on files picked up by the crawlers
	 * @param numCrawlActionThreads
	 *            - number of crawl action threads to create
	 * @param throttleActionThreads
	 *            - the amount of time to throttle the action thread in millis. Zero for no throttling.
	 * @param fileQueueCapacity
	 *            - the capacity of the internal file queue used to buffer the files
	 * @throws InterruptedException
	 */
	public static final void crawl(File dir, FileFilter filter, int numCrawlThreads, long throttleCrawlThreads, CrawlAction action, int numCrawlActionThreads,
			long throttleActionThreads, int fileQueueCapacity) throws InterruptedException
	{
		final LinkedBlockingQueue<File> fileQueue = new LinkedBlockingQueue<File>(fileQueueCapacity);
		final ExecutorService consumerExecutor = createAndStartConsumers(action, fileQueue, numCrawlActionThreads, throttleActionThreads);
		final ExecutorService producerExecutor = createAndStartProducers(dir, filter, fileQueue, numCrawlThreads, throttleCrawlThreads);

		producerExecutor.shutdown();
		producerExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

		for (int i = 0; i < numCrawlActionThreads; i++)
		{
			fileQueue.put(POISON_PILL);
		}

		consumerExecutor.shutdown();
		consumerExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}

	/**
	 * 
	 * <b>Note:</b> While the number of threads specified will be created, there is no guarantee that all of the threads will pick
	 * up work. This is due to two factors. <br/>
	 * 1. The randomness of work assignment. <br/>
	 * 2. The available parallelism to exploit. For example if there is one directory to scan and no sub-directories but a 100
	 * threads are created, then it is very likely that 99 threads will have nothing to do and will run to completion without ever
	 * picking up work.
	 */
	private static final ExecutorService createAndStartProducers(File dir, FileFilter filter, Queue<File> fileQueue, int numThreads, long throttleTimeInMillis)
			throws InterruptedException
	{
		// Since the FileCralwerProducer is essentially its own consumer (of directories), 
		// the directory queue must be an unbounded queue or a deadlock may occur.
		final PriorityBlockingQueue<File> dirQueue = new PriorityBlockingQueue<File>();
		dirQueue.add(dir);

		final ExecutorService producerExecutor = Executors.newFixedThreadPool(numThreads);
		for (int i = 0; i < numThreads; i++)
		{
			producerExecutor.execute(new FileCrawlerProducer(filter, dirQueue, fileQueue, throttleTimeInMillis));
		}

		return producerExecutor;
	}

	private static final ExecutorService createAndStartConsumers(CrawlAction action, LinkedBlockingQueue<File> fileQueue, int numThreads,
			long throttleTimeInMillis)
	{
		final ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		for (int i = 0; i < numThreads; i++)
		{
			executor.execute(new FileCrawlerConsumer(action, fileQueue, throttleTimeInMillis));
		}

		return executor;
	}

	// ------------------------------------------------------------------------------
	// ----------------------------  Inner Class ------------------------------------
	// ------------------------------------------------------------------------------

	private static final class FileCrawlerProducer implements Runnable
	{
		private static final long TIMEOUT_IN_MILLIS = 500;
		private final FileFilter filter;
		private final PriorityBlockingQueue<File> dirQueue;
		private final Queue<File> fileQueue;
		private final long throttleTimeInMillis;

		private FileCrawlerProducer(FileFilter filter, PriorityBlockingQueue<File> dirQueue, Queue<File> fileQueue, long throttleTimeInMillis)
		{
			super();
			this.filter = filter;
			this.dirQueue = dirQueue;
			this.fileQueue = fileQueue;
			this.throttleTimeInMillis = throttleTimeInMillis;
		}

		@Override
		public void run()
		{
			try
			{
				while (!Thread.currentThread().isInterrupted())
				{
					if (throttleTimeInMillis > 0)
					{
						synchronized (this)
						{
							wait(throttleTimeInMillis);
						}
						//Thread.sleep(throttleTimeInMillis);
					}

					final File dir = dirQueue.poll(TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS);
					if (dir == null)
					{
						break;
					}

					crawl(dir);
				}
			}
			catch (final InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}

		private void crawl(File root) throws InterruptedException
		{
			final File[] entries = (filter != null) ? root.listFiles(filter) : root.listFiles();
			for (final File file : entries)
			{
				if (file.isDirectory())
				{
					dirQueue.put(file);
				}
				else
				{
					addToFileQueue(file);
				}
			}
		}

		/**
		 * Blocks if the queue is an instance of BlockingQueue, otherwise it is non-blocking.
		 */
		private void addToFileQueue(File file) throws InterruptedException
		{
			if (fileQueue instanceof BlockingQueue<?>)
			{
				((BlockingQueue<File>) fileQueue).put(file);
			}
			else
			{
				fileQueue.add(file);
			}
		}

	}

	private static final class FileCrawlerConsumer implements Runnable
	{
		private final CrawlAction action;
		private final LinkedBlockingQueue<File> fileQueue;
		private final long throttleTimeInMillis;

		public FileCrawlerConsumer(CrawlAction action, LinkedBlockingQueue<File> fileQueue, long throttleTimeInMillis)
		{
			super();
			this.action = action;
			this.fileQueue = fileQueue;
			this.throttleTimeInMillis = throttleTimeInMillis;
		}

		@Override
		public void run()
		{
			try
			{
				while (!Thread.currentThread().isInterrupted())
				{
					if (throttleTimeInMillis > 0)
					{
						synchronized (this)
						{
							wait(throttleTimeInMillis);
						}
						//Thread.sleep(throttleTimeInMillis);
					}

					final File file = fileQueue.take();
					if (file == FileCrawler.POISON_PILL)
					{
						break;
					}

					action.doAction(file);
				}
			}
			catch (final InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}

	}

}
