package com.lly.test.thread.parallel;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 信号量控制并发
 * @author leliu
 */
public class SemaphoreTest {

	private static Semaphore semaphore = new Semaphore(5);

	private AtomicInteger total = new AtomicInteger(100);


	/**
	 * 模拟并发120
	 */
	@Test
	public void test() {
		CountDownLatch downLatch = new CountDownLatch(120);
		CountDownLatch prepareLatch = new CountDownLatch(1);
		ScheduledExecutorService service = Executors.newScheduledThreadPool(6);

		for (int i = 0; i < 120; i++) {
			final int t = i;

			service.execute(() -> {
				try {
					System.out.println("prepare [" + t +"]");
					prepareLatch.await();
					semaphore.acquire();
					seckill();
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				} finally {
					semaphore.release();
					downLatch.countDown();
				}
			});
		}


		try {
			TimeUnit.MILLISECONDS.sleep(1000);
			prepareLatch.countDown();
			downLatch.await();

			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
		}
	}



	/**
	 * 模拟并发请求业务
	 */
	public void seckill(){
		if(total.get() > 0) {
			System.out.println("当前数量：["+ total.get() +"]开始减商品库存信息。。。。。");
		} else {
			System.out.println("甩卖结束!");
		}
		total.getAndDecrement();
	}
}
