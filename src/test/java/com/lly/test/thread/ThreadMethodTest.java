package com.lly.test.thread;

import org.junit.Test;

/**
 * 测试线程的原生方法
 * @author leliu
 * @version V1.0
 * @date 2018/11/19 19:20
 */
public class ThreadMethodTest {

	/**
	 * 线程接力，前一个线程跑完后后一个线程再继续
	 * join 方法：当前线程会等待调用该方法的线程
	 *   比如在线程B中： threadA.join();
	 *   那么线程B会等待threadA执行完成后再执行
	 *
	 * sleep 和 wait:
	 *    sleep是静态方法，wait是类方法。
	 *    sleep只会释放出cpu的时间，但是不会释放锁，但是wait会释放锁，并进入等待池中，直到时间到或者
	 *    被notify激活离开等待池,然后等待cpu分配时间。
	 *    wait释放锁，所以必须在同步代码块中执行，也就是在 synchronized 方法中执行
	 *
	 * sleep 和 yield：
	 *    目的一样：都是释放出当前线程的cpu时间。
	 *    结果不一样，sleep释放的cpu时间所有的线程都可以竞争，但是yield释放的cpu时间只有同等优先级的
	 *    线程才可以有机会竞争
	 */
	@Test
	public void testJoin(){
		Thread previousThread = new Thread();
		for (int i = 0; i < 10; i++) {
			JoinThread joinThread = new JoinThread(previousThread);
			joinThread.start();
			previousThread = joinThread;
		}

		// 等待子线程跑完，否则在@Test中不会执行子线程
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}





/* -------------------------静态内部类---------------------------------------------*/
	private static class JoinThread extends Thread{
		private Thread thread;

		public JoinThread(Thread thread) {
			this.thread = thread;
		}

		@Override
		public void run() {
			try {
				// 当前线程会等待thread执行完后再执行,就保证了线程的顺序
				thread.join();
				System.out.println(thread.getName() + " isTeminated");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
