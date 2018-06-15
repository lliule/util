package com.lly.test.thread.future;

import com.lly.util.DownLoad;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 一个线程下载图片
 * url:[
 *   https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3943188969,2723503656&fm=58
 *   https://ss3.baidu.com/-rVXeDTa2gU2pMbgoY3K/it/u=682588704,4048383846&fm=202&mola=new&crop=v1
 *   ]
 * 一个线程读取文本
 */
public class FutureTest {
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        download(Arrays.asList("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3943188969,2723503656&fm=58",
                "https://ss3.baidu.com/-rVXeDTa2gU2pMbgoY3K/it/u=682588704,4048383846&fm=202&mola=new&crop=v1"));
    }
    public static void download(List<String> images){
        // 下载照片
        Callable<List<Boolean>> callable = ()->{
            ArrayList<Boolean> results = new ArrayList<>(images.size());
            images.forEach((url)->{
                results.add(down(url));
            });
            return results;
        };

        Future<List<Boolean>> future = executor.submit(callable);
        // 读取文本
//        Callable<String> readText = FutureTest::read;
        String read = read();
        System.out.println(read);
        try {
            List<Boolean> list = future.get();
            System.out.println(Arrays.toString(list.toArray()));
        } catch (InterruptedException e) {
            //声名线程的中断状态
            Thread.currentThread().interrupt();
            //不需要结果，故取消任务
            future.cancel(true);
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
        }
    }


    static boolean down(String url){
        try {
            DownLoad.download(url,"D:/temp", new Random().nextInt()+".png");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String read(){
        try {
            FileInputStream stream = new FileInputStream("D:\\work\\new 1.txt");
            BufferedReader reader = new BufferedReader(new FileReader("D:\\work\\new 1.txt"));
            String line = reader.readLine();
            return line;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
