package com.lly.spi.jdk;

/**
 * @author leliu
 * @date 2019/6/20 9:32
 */
public class SpiService2Impl implements SpiService{
	@Override
	public void call() {
		System.out.println(this.getClass().getName() + " is running.....");
	}
}
