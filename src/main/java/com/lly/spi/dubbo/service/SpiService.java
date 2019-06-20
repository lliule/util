package com.lly.spi.dubbo.service;

import com.lly.spi.dubbo.annotation.DanaSpi;

/**
 *
 * @author leliu
 * @date 2019/6/20 11:49
 */
@DanaSpi("print")
public interface SpiService {

	void call();
}
