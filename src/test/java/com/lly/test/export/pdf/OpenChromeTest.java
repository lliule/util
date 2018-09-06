package com.lly.test.export.pdf;

import com.lly.common.HttpRequest;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 打开chrome 浏览器
 */
public class OpenChromeTest {

    @Test
    public void testOpenChrome() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe", "www.baidu.com");
        builder.start();
    }

    @Test
    public void testOpenChromeWithMin() throws IOException {
        ProcessBuilder minChrome = new ProcessBuilder("start /MIN chrome", "www.baidu.com");
        minChrome.start();
    }


    /**
     * 测试open 的并发
     */
    @Test
    public void testSyncOpenChrome() {
        long startTime = System.currentTimeMillis();
        String url = "http://localhost:3000/openChrome?url=http://www.baidu.com";

        for (int i = 0; i < 10; i++) {
            open(url);
        }
        long endTime = System.currentTimeMillis();

        System.out.println("totleTime = " + (endTime - startTime) + " ms/ " + (endTime - startTime) / 1000 + "s");

    }

    private void open(String url) {
        System.out.println("----");
        HttpRequest request = new HttpRequest();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ArrayList<Future<Boolean>> list = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            Future<Boolean> submit = executorService.submit(new OpenChromeCall(url, request));
            list.add(submit);
        }
        list.forEach((f) -> {
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    class OpenChromeCall implements Callable<Boolean> {
        private String url;
        private HttpRequest request;

        public OpenChromeCall(String url, HttpRequest request) {
            this.url = url;
            this.request = request;
        }

        @Override
        public Boolean call() throws Exception {
            request.getRequest(url);
            return true;
        }
    }



}
