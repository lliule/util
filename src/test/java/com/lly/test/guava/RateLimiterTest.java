package com.lly.test.guava;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

public class RateLimiterTest {

    public static void main(String[] args) {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(100));

        // 每秒放1个令牌
        RateLimiter rateLimiter = RateLimiter.create(1);

        for (int i = 0; i < 50; i++) {
            Double acquire = null;
            if(i == 1) {
                acquire = rateLimiter.acquire(1);
            } else if(i == 2) {
                acquire = rateLimiter.acquire(10);
            } else if(i == 3) {
                acquire = rateLimiter.acquire(2);
            }else if(i == 4) {
                acquire = rateLimiter.acquire(20);
            }else {
                acquire = rateLimiter.acquire(2);
            }

            service.submit(new Task("获取令牌成功，获取耗：" + acquire + ", 第" + i + "个任务执行"));
        }

    }


    static class Task implements Runnable{
        String str;

        public Task(String str) {
            this.str = str;
        }

        @Override
        public void run() {
            String format = DateFormat.getDateTimeInstance().format(new Date());
            System.out.println(format + "|" + Thread.currentThread().getName() + str);
        }
    }
}
