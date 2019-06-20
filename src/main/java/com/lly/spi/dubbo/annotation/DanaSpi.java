package com.lly.spi.dubbo.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DanaSpi {

	/**
	 * 扩展点名
	 */
	String value() default "";

}
