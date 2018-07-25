package com.lly.test.algol;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class While {


    private int init = 5;
    @Test
    public void testWhile(){
        int i = 3;
        boolean b = get();
        while (!b){
            if(i>0){
                System.out.println("i = " + i);
                b = get();
                i--;
            }else {
                break;
            }
        }
        System.out.println("b = "+ b);
    }


    private boolean get( ){
        init --;
        if (init == 0){
            return true;
        }else{
            return  false;
        }
    }


    @Test
    public void testInvakeAll() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ArrayList<Test1> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new Test1(i));
        }
        List<Future<Boolean>> futures = executorService.invokeAll(list);
        for (Future<Boolean> future : futures) {
            System.out.println(future.get());
        }


    }

    private class Test1 implements Callable<Boolean> {
        private int i;

        public Test1(int i) {
            this.i = i;
        }

        @Override
        public Boolean call() throws Exception {
            System.out.println(Thread.currentThread().getName() + "---i---" + i );
            return false;
        }
    }

}
