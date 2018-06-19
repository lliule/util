package com.lly.test.thread.logDemo.test;

import com.lly.test.thread.logDemo.LogServiceExecutor;

import java.io.PrintWriter;

public class LogServiceExecutorTest {

    public static void main(String[] args) {
        LogServiceExecutor executor = new LogServiceExecutor(new PrintWriter(System.out));
        try {
            executor.log("dana");
            executor.start();
        } catch (InterruptedException e) {
        }
    }


}
