package com.lly.test.thread.executor;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author leliu
 */
public class ExecutorTest {

	@Test
	public void testThreadPoolExecutor(){
		ThreadPoolExecutor threadPoolExecutor;
		ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(100);
		// 设置存储线程池的消息队列。这里一旦队列超过100，我们没有指定队列满出的策略，默认使用终止策略。既，
		// 抛出java.util.concurrent.RejectedExecutionException异常
		threadPoolExecutor = new ThreadPoolExecutor(3, 3,
				60L, TimeUnit.SECONDS,blockingQueue);

		// 指定队列堆满时的策略： 丢弃最旧的队列信息。此时任务不会终止，但是队列会丢失。
//		threadPoolExecutor = new ThreadPoolExecutor(3,

//				1, TimeUnit.SECONDS, blockingQueue, new ThreadPoolExecutor.DiscardOldestPolicy());

		// 关于队列堆满时的策略，有4中：终止，丢弃最旧的队列、丢弃策略和调用者饱和策略。丢弃策略表示不会再接受新的队列信息

		// 调用者饱和策略
//		threadPoolExecutor = new ThreadPoolExecutor(3,3,
//				1,TimeUnit.SECONDS,blockingQueue,new ThreadPoolExecutor.CallerRunsPolicy());

		do{
//			try{

			//  我们可以在这里保证队列的size，当队列超过指定个数，主线程就暂停接受其他队列信息。
			/*if(blockingQueue.size() > 50){
				do {
					System.out.println("队列超过预警值: " + blockingQueue.size());
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(blockingQueue.size() <= 30){
						break;
					}
				}while (true);
			}*/

			threadPoolExecutor.execute(()->{
				String name = Thread.currentThread().getName();
				System.out.println(name);
			});
//			}catch (RejectedExecutionException e){
//				System.out.println("blocking queue size = " +blockingQueue.size());
//				blockingQueue.clear();
//			}

		}while (true);


	}


	/**
	 * 为了模拟出OOM，限制 jvm 的内存大小
	 *  -Xms5m -Xmx5m
	 *
	 * 抛出异常：java.lang.OutOfMemoryError: GC overhead limit exceeded
	 *
	 * 这种OOM,是因为FixedThreadPool的任务队列没有限制导致的，当任务队列过大，jvm会启用GC尝试
	 * 回收无用的垃圾数据，但是由于队列还被线程池拥有，无法被回收导致的OOM。
	 * 和CachedThreadPool的OOM不同。
	 * CachedThreadPool产生OOM是因为线程池是可以无线创建造成的堆栈空间OOM。{@link #testCachedThreadPool}
	 */
	@Test
	public void testFixedThreadPool(){
		// 下面的代码必会抛出OOM。因为存放任务的队列是LindedBlockingQueue，是一个可以存放无限多任务的队列。
		// 但是消费任务的线程数有限，当来不及消费导致任务队列堆积，
		// 在内存有限的情况下，队列一直添加必然会溢出导致OOM。
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		do{
			executorService.execute(()->{
				String name = Thread.currentThread().getName();
				System.out.println(name);
			});
		}while (true);
	}


	/**
	 * 测试 CacheedThreadPool线程池
	 * 该类型线程池特点：当没有任务的时候，所有的线程都会被回收，当任务批量过来，
	 * 可以创建N个线程去处理任务。当一个线程消费完当前任务，超过1分钟没有获取到新任务后，将被回收。
	 */
	@Test
	public void testCachedThreadPool(){
		// 以下代码会 java.lang.OutOfMemoryError: Java heap space
		// 但是偶发，不会想FixThreadPool那样
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		do{
			cachedThreadPool.execute(()->{
				String name = Thread.currentThread().getName();
				System.out.println(name);
			});
		}while (true);
	}
}
