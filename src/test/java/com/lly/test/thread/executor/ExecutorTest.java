package com.lly.test.thread.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
		// 但是偶发，不会向FixThreadPool那样频发出现
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		do{
			cachedThreadPool.execute(()->{
				String name = Thread.currentThread().getName();
				System.out.println(name);
			});
		}while (true);
	}


	/**
	 * 自定义线程工厂，实现ThreadFactory接口
	 */
	@Test
	public void testThreadFactory(){
		// 自定义线程工厂方法1；
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10,
				60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new MyThreadFactory("main"));
		// 自定义线程工厂方法2：
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-thread-%d").build();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10,
				60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), threadFactory);
		for (int i = 0; i < 100; i++) {
			executor.execute(()->{
				System.out.println(Thread.currentThread().getName());
			});
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自定义线程工厂
	 */
	static class MyThreadFactory implements ThreadFactory{
		private static  AtomicInteger poolNum = new AtomicInteger(1);
		private String modelName;
		public MyThreadFactory(String name){
			this.modelName = name;
		}
		@Override
		public Thread newThread(@Nonnull Runnable r) {
			return new Thread(r,"myThread--" + modelName + "-" + poolNum.getAndIncrement());
		}
	}


	/**
	 * SingleThreadExecutor
	 * 创建唯一一个工作线程，去执行一个无限工作队列的队列任务。
	 * 当当前工作线程异常终止，会再去生成一个线程继续执行队列的任务。确保任务会有一个且仅有一个执行
	 *
	 * 原理：创建ThreadPoolExecutor,指定最大线程数为1，当当先线程终止，queue中又有任务会自动创建线程执行任务队列
	 *
	 */
	@Test
	public void testSingleThread(){
		/*ExecutorService executorService = Executors.newSingleThreadExecutor();
		do{
			executorService.execute(()->{
				System.out.println(Thread.currentThread().getName());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int i = 1/0;
			});
		}while(true);
*/

		// 模拟 singleThread
		LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
		Runnable r1 = ()->{System.out.println(1);int i = 1/0;};
		Runnable r2 = ()->{System.out.println(2);int i = 1/0;};
		Runnable r3 = ()->{System.out.println(3);int i = 1/0;};
		Runnable r4 = ()->{System.out.println(4);int i = 1/0;};
		Runnable r5 = ()->{System.out.println(5);int i = 1/0;};
		List<Runnable> runnableList = Arrays.asList(r1, r2, r3, r4, r5);

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
				0L, TimeUnit.MILLISECONDS, queue);

		for (Runnable runnable : runnableList) {
			threadPoolExecutor.execute(runnable);
		}
	}


	@Test
	public void testSchedulePool(){
		Executors.newSingleThreadScheduledExecutor();
	}


}



