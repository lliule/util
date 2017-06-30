package com.lly.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class LogUtil {

    private static String path = "F:/work/util/";
    public static void log(String content) throws IOException {
        LocalDate now = LocalDate.now();
        String fileName = ""+now.getYear()+now.getMonthValue()+now.getDayOfMonth()+".log";
        File file = new File(path + fileName);
        FileOutputStream fileOutputStream = null;
        FileWriter fileWriter = null;
        if(file.exists()){
            try {
                fileWriter = new FileWriter(file, true);
                fileWriter.write(content);
            }finally {
                assert fileWriter!= null;
                fileWriter.close();
            }
        }else{
            try {
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(content.getBytes());
            } finally {
                assert fileOutputStream != null;
                fileOutputStream.close();
            }
        }
    }
}
