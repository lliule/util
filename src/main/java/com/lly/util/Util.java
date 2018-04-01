package com.lly.util;

import java.io.*;
import java.util.ArrayList;

/**
 * @author :dana
 * @since ：1.0
 */
public class Util {

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
}
