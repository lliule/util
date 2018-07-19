package com.lly.read;

import ch.qos.logback.core.util.FileUtil;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;

/**
 * @author :dana
 * @since ：1.0
 */
public class Read2String {

    /**
     * 按行读取文件中的信息，返回每行作为元素的字符串数组
     * @param file 文件路径
     * @return String[]
     * @throws FileNotFoundException
     */
    public static String[] readFileToArray(String file) throws FileNotFoundException {
//        FileInputStream inputStream = new FileInputStream(file);
        FileReader reader = new FileReader(file);
        ArrayList<String> list = new ArrayList<>();
        String[] strings = {};
        BufferedReader reader1 = new BufferedReader(reader);
        String t = null;
        try {
            while (((t = reader1.readLine()) != null)){
                list.add("\""+t+"\"");
            }
            reader1.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(strings);
    }

    /**
     * 按行读取文件并返回字符串
     * @param file filePath
     * @return
     * @throws FileNotFoundException
     */
    public static String readFileToString(String file) throws IOException {
        return FileUtils.readFileToString(new File(file),"UTF-8");
        /*FileInputStream inputStream = new FileInputStream(file);
        StringBuffer buffer = new StringBuffer();
        byte[] temp = new byte[1024];
        while (inputStream.read(temp) > -1){
            buffer.append(new String(temp,"UTF-8"));
        }
        return buffer.toString();*/

    }
}
