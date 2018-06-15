package com.lly.test.thread.cancel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimeGenerator implements Runnable{

    private List<BigInteger> primeList = new ArrayList<BigInteger>();
    private volatile boolean cancelled;

    private static int getNextPrime(int x){
        while (true){
            int y = ++x;
            for (int i = 2; i < Math.sqrt(y+1); i++) {
                if( y % i == 0){
                    return y;
                }
            }
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start ...");
        BigInteger one = BigInteger.ONE;
        while(!cancelled){
            one = one.nextProbablePrime();
            synchronized(this){
                primeList.add(one);
            }
        }
    }

    public synchronized List<BigInteger> get(){
        return new ArrayList<>(primeList);
    }

    public void cancel(){
        System.out.println("cancel...");
        cancelled = true;
    }
}
