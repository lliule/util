package com.lly.test.pdf;

import org.junit.Test;

import java.io.IOException;

/**
 * 打开chrome 浏览器
 */
public class OpenChromeTest {

    @Test
    public void testOpenChrome() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe", "www.baidu.com");
        builder.start();
    }

    @Test
    public void testOpenChromeWithMin() throws IOException {
        ProcessBuilder minChrome = new ProcessBuilder("start /MIN chrome", "www.baidu.com");
        minChrome.start();
    }

}
