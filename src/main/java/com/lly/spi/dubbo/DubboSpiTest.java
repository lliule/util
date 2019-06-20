package com.lly.spi.dubbo;

import com.lly.spi.dubbo.loader.DanaExtensionLoader;
import com.lly.spi.dubbo.service.SpiService;

/**
 *
 * @author leliu
 * @date 2019/6/20 16:06
 */
public class DubboSpiTest {

	public static void main(String[] args) {
		DanaExtensionLoader<SpiService> loader = DanaExtensionLoader.getExtensionLoader(SpiService.class);
		SpiService log = loader.getExtension("print");
		log.call();
	}
}
