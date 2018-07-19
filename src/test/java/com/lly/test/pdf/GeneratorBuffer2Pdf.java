package com.lly.test.pdf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lly.common.HttpRequest;
import com.lly.read.Read2String;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class GeneratorBuffer2Pdf {

    // 请求生成pdf
    @Test
    public void generatorPdf() throws IOException {
        String file = "D:\\work\\智提分\\个性化答题卡.html";
        String url = "http://localhost:3000/pdf";
        HashMap<String, String> map = new HashMap<>();
        map.put("pageSize","A4");
        String fileContent = Read2String.readFileToString(file);
        map.put("html",fileContent);
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(map);
        HttpRequest request = new HttpRequest();
        InputStream inputStream = request.postRequestWithJson(url, value);
        int count = 0;
        if (count == 0){
            count = inputStream.available();
        }
        System.out.println(count);
        FileOutputStream outputStream = new FileOutputStream("test.pdf");
        byte[] temp = new byte[1024];
        while (inputStream.read(temp) > 0){
            outputStream.write(temp);
        }
        inputStream.close();

        outputStream.flush();
        outputStream.close();
    }
}
