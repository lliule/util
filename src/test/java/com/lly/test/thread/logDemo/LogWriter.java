package com.lly.test.thread.logDemo;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 该例子中，要关闭日志很难，
 * 不但要关闭消费者(start)，还要关闭生产者（log）
 * 而且有可能queue中的队列已满，导致生产者阻塞在log()中，
 * 不可能解放出来
 */
public class LogWriter {

    private final BlockingQueue<String> queue;

    private LogThread log;

    public LogWriter(PrintWriter writer) {
        queue = new LinkedBlockingQueue<>(1000);
        log = new LogThread(writer);
    }

    public void start(){
        log.start();
    }
    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    private class LogThread extends Thread{
        private final PrintWriter writer;

        public LogThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            while (true){
                try {
                    writer.println(queue.take());
                } catch (InterruptedException e) {

                }finally {
                    writer.close();
                }
            }
        }
    }


}
