package com.lly.spi.dubbo.util;

import lombok.Data;

/**
 *
 * @author leliu
 * @date 2019/6/20 11:45
 */
@Data
public class Holder<T> {

	private volatile T value;

}
