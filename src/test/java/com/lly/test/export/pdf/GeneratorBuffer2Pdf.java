package com.lly.test.export.pdf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lly.common.HttpRequest;
import com.lly.read.Read2String;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.*;

public class GeneratorBuffer2Pdf {
    String url = "http://local.test.com/pdf";
    // 请求生成pdf
    // params: htmlContent
    // return :stream
    @Test
    public void generatorPdf() throws IOException {
        String value = getHtml(null);
        getPdf(value,null);
    }

    private void getPdf(String value,String targetFile) throws IOException {
        HttpRequest request = new HttpRequest();
        InputStream inputStream = request.postRequestWithJson(url, value);
        FileOutputStream outputStream = new FileOutputStream(targetFile == null ? "test.pdf" : targetFile);
        byte[] temp = new byte[1024];
        while (inputStream.read(temp) > 0){
            outputStream.write(temp);
        }
        inputStream.close();

        outputStream.flush();
        outputStream.close();
    }

    public void genPdf(String filePath,String targetFile) throws IOException {
        String html = getHtml(filePath);
        getPdf(html,targetFile);
    }


    private String getHtml(String filePath) throws IOException {
        String file = filePath == null ?  "D:\\devlopment\\js-workspace\\puppeteer\\test\\html-pdf\\ans.html" : filePath;
        HashMap<String, String> map = new HashMap<>();
        map.put("pageSize","A4");
        String fileContent = Read2String.readFileToString(file);
        map.put("html",fileContent);
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(map);
        return value;
    }

    @Test
    public void testGenPDF() throws IOException {
        long startTime = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(10);
        ArrayList<Future<Boolean>> list = new ArrayList<>();
        String file = "D:\\work\\ztf\\ans";
        for(int j = 0 ;j < 10; j++) {
            for (int i = 1; i < 21; i++) {
                Future<Boolean> submit = service.submit(new Pdf(file + i + ".html", "test/test" + UUID.randomUUID().toString() + ".pdf"));
                list.add(submit);
            }
        }

        list.forEach((f)->{
            try {
                f.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        long endTime = System.currentTimeMillis();
        System.out.println("all total time = " + (endTime - startTime) / 1000 );

    }

    class Pdf implements Callable<Boolean> {
        private String filePath;
        private String targetFile;

        public Pdf(String filePath, String targetFile) {
            this.filePath = filePath;
            this.targetFile = targetFile;
        }

        @Override
        public Boolean call() throws Exception {
            try{
                genPdf(filePath,targetFile);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }



}
