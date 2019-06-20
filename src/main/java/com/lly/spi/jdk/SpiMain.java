package com.lly.spi.jdk;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 *
 * @author leliu
 * @date 2019/6/20 9:34
 */
public class SpiMain {

	public static void main(String[] args) {
		// 加载 META-INF/serivces目录下的 SpiService.class.getName() 的文件
		ServiceLoader<SpiService> load = ServiceLoader.load(SpiService.class);

		Iterator<SpiService> iterator = load.iterator();

		while (iterator.hasNext()) {
			iterator.next().call();
		}

	}
}
