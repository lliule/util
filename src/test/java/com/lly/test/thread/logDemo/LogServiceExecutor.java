package com.lly.test.thread.logDemo;

import java.io.PrintWriter;
import java.util.concurrent.*;

public class LogServiceExecutor {


    private ExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private final BlockingQueue<String> queue;
    private final LogThread logThread;

    public LogServiceExecutor(PrintWriter writer) {
        queue = new LinkedBlockingDeque<>(1000);
        logThread = new LogThread(writer);
    }

    public void start(){
        service.execute(logThread);
//        logThread.start();
    }
    public void stop() throws InterruptedException {
        service.shutdown();
        service.awaitTermination(1,TimeUnit.SECONDS);
    }
    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    private class LogThread extends Thread {
        private PrintWriter writer;

        public LogThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                while (true){
                    System.out.println("log start");
                    try {
                        String msg = queue.take();
                        writer.println(msg);
                    } catch (InterruptedException e) {
                        System.out.println("ignore error");
                    }
                }
            }finally {
                writer.close();
            }
        }
    }
}
