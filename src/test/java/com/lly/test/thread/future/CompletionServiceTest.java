package com.lly.test.thread.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class CompletionServiceTest {

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        render();
    }


    /**
     * 使用CompletionService 对多个下载请求
     * 并行执行，并且只要一个完成就可以返回结果。
     */
    public static void render(){
        List<String> urlList = Arrays.asList("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3943188969,2723503656&fm=58",
                "https://ss3.baidu.com/-rVXeDTa2gU2pMbgoY3K/it/u=682588704,4048383846&fm=202&mola=new&crop=v1");
        CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executorService);
        for (String url : urlList) {
             completionService.submit(()->FutureTest.down(url));
        }
        read();

        for (int i = 0; i < urlList.size(); i++) {
            try {
                Future<Boolean> take = completionService.take();
                Boolean reuslt = take.get();
                System.out.println("down " + i + " : "+ reuslt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private static void read() {
        Future<?> read = executorService.submit(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("read over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("read done  : " +read.isDone());
    }
}
