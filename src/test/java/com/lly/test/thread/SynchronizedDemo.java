package com.lly.test.thread;

/**
 *  多线程并发计算求和
 * @author leliu
 * @version V1.0
 * @date 2018/11/20 14:35
 */
public class SynchronizedDemo{

	public static void main(String[] args) {
		new SynchronizedDemo1().calculate();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 并发计算
	 */
	private static int count = 0;
	private static class SynchronizedDemo1 extends Thread{
		public void calculate() {
			for (int i = 0; i < 10; i++) {
				new SynchronizedDemo1().start();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(count);
		}


		@Override
		public void run() {
			synchronized (SynchronizedDemo1.class){
				for (int i = 0; i < 10000; i++) {
					count++;
				}
			}
		}
	}
}
