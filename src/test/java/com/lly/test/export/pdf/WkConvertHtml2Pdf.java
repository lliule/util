package com.lly.test.export.pdf;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.*;

public class WkConvertHtml2Pdf {

    private static String pdfPath = "wktets.pdf",
            htmlPath = "D:\\devlopment\\js-workspace\\puppeteer\\test\\html-pdf\\ans.html",
            file = "D:\\work\\ztf\\ans";

    public static void main(String[] args) {
        ArrayList<Future<Boolean>> list = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for(int j = 0 ;j < 2; j++) {
            for (int i = 1; i < 10; i++) {
                Future<Boolean> submit = service.submit(new Pdf(htmlPath, "test1/test" + UUID.randomUUID().toString() + ".pdf"));
                list.add(submit);
            }
        }

        list.forEach((f)->{
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * 执行html转成pdf
     */
    public static void main2(String ...args){

        long startTime = System.currentTimeMillis();
        StringBuffer commandBuffer = new StringBuffer();
        String rootPath = WkConvertHtml2Pdf.class.getClassLoader().getResource("").getPath();
        commandBuffer.append(rootPath);
        if(System.getProperty("os.name").contains("Windows")){
            //新版本的组件，解决高清图片重影问题  --javascript-delay 3000
            //commandBuffer.append("wkhtmltopdf_v13-alpha_x64.exe --page-size  ");
            if(System.getProperty("os.arch").equals("x86")){
                commandBuffer.append("wkhtmltopdf32.exe --page-size  ");
            }else{
                commandBuffer.append("wkhtmltopdf64.exe --page-size  ");
            }
        }else{
            commandBuffer.append("./wkhtmltopdf --page-size  ");
        }
//        if(page.startsWith("A3")) {
//            page = "A3";
//        }
        commandBuffer.append("A4");
//        if("A3".equals(page)){
//            commandBuffer.append(" --orientation Landscape ");
//        }
        commandBuffer.append(" --margin-top 0mm --margin-left 0mm --margin-right 0mm --margin-bottom 0mm --disable-smart-shrinking --dpi ");
        commandBuffer.append(Toolkit.getDefaultToolkit().getScreenResolution());
        commandBuffer.append(" --encoding utf-8 ");
        commandBuffer.append(htmlPath);
        commandBuffer.append(" ");
        commandBuffer.append(pdfPath);

        Process process = null;
        BufferedReader br = null;
        try {
            process = Runtime.getRuntime().exec(commandBuffer.toString());
//            InputStreamReader inputStreamReader = new InputStreamReader(
//                    process.getErrorStream());
//            br = new BufferedReader(inputStreamReader);
//            String line;
//            while ((line = br.readLine()) != null) {
////                System.out.println(line);
//            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (process != null) {
                process.destroy();
            }
            process = null;
            if(null != br){
                try {
                    br.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("total time = " + (endTime - startTime));
    }

    static class Pdf implements Callable<Boolean> {
        private String filePath;
        private String targetFile;
        private GeneratorBuffer2Pdf gen;

        public Pdf(String filePath, String targetFile) {
            this.filePath = filePath;
            this.targetFile = targetFile;
            gen = new GeneratorBuffer2Pdf();
        }

        @Override
        public Boolean call() throws Exception {
            try{
//                gen(filePath,targetFile);
                gen.genPdf(filePath,targetFile);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
}
