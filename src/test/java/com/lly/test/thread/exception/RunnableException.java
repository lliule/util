package com.lly.test.thread.exception;

/**
 * try catch 无法捕获线程中的异常
 */
public class RunnableException {
    public static void main(String[] args) {
        Runnable run = ()->{
                System.out.println("run");
                int i = 1/0;
                System.out.println(i);
        };
        try {
            new Thread(run).start();
        }catch (Exception e){
            System.out.println("exception run");
        }
    }
}
