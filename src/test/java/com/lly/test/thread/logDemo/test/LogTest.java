package com.lly.test.thread.logDemo.test;

import com.lly.test.thread.logDemo.LogWriter;

import java.io.PrintWriter;

public class LogTest {

    public static void main(String[] args) throws InterruptedException {
        PrintWriter printWriter = new PrintWriter(System.out);
        LogWriter writer = new LogWriter(printWriter);
        writer.start();
        writer.log("dana");
    }

}
