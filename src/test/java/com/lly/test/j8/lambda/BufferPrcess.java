package com.lly.test.j8.lambda;

import java.io.BufferedReader;

/**
 * 函数式接口，定义行为
 */
public interface BufferPrcess {

    String process(BufferedReader b) throws Exception;

}
