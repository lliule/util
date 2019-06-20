package com.lly.spi.dubbo.service;

import com.lly.spi.dubbo.annotation.DanaSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author leliu
 * @date 2019/6/20 11:50
 */
@DanaSpi("log")
public class LogSpiServiceImpl implements SpiService{

	private static final Logger logger = LoggerFactory.getLogger(LogSpiServiceImpl.class);

	@Override
	public void call() {
		logger.error("record log spi service...");
	}
}
