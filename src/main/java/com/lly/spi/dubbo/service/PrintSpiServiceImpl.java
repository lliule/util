package com.lly.spi.dubbo.service;

import com.lly.spi.dubbo.annotation.DanaSpi;

/**
 *
 * @author leliu
 * @date 2019/6/20 11:53
 */
@DanaSpi("print")
public class PrintSpiServiceImpl implements SpiService{
	@Override
	public void call() {
		System.out.println("print by sout....");
	}
}
