package com.lly.test.thread.logDemo;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 能够优雅的关闭logService:
 *  queue中的队列全部消费完
 *  但是一个奇怪的现象:在@code{
 *      LogServiceTest
 *  }中测试的时候，我们发现，log必须等到主线程完成后才会执行。
 *  接下来改造使用线程单独执行
 */
public class LogService {
    private final BlockingQueue<String> queue;
    private final LogThread logThread;
    private int revertion;
    private boolean isShutdown;

    public LogService(PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<>(1000);
        this.logThread = new LogThread(writer);
    }

    public void start(){
        logThread.start();
    }

    public void stop(){
        synchronized(this){
            isShutdown = true;
        }
        logThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized(this){
            if(isShutdown){
                System.out.println(msg + " : consumer is shutdown");
                return;
            }else{
                revertion ++;
            }
        }
        queue.put(msg);
    }

    private class LogThread extends Thread{
        private PrintWriter writer;

        public LogThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                while (true){
                   synchronized (LogService.this){
                        if(isShutdown && revertion == 0){
                            break;
                        }
                   }
                    try {
                        String msg = queue.take();
                        synchronized (LogService.this){
                            --revertion;
                        }
                        writer.println(msg);
                    } catch (InterruptedException e) {
                        System.out.println("63 L is error");
                    }
                }
            } finally {
                writer.close();
            }
        }
    }

}
