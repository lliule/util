package com.lly.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFileToJson {

    /**
     * 读取json数组文件，返回数组list集合
     * @param file 指定文件全路径
     * @return list
     */
    public static List<String> readJson(String file){
        String result = "";
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String s  =null;
            while((s= reader.readLine())!= null){
                try {
                    result += s;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            reader.close();
            JSONArray array = new JSONArray(result);
            for (Object anArray : array) {
                list.add((String) anArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 读取json数组对象
     */
    public static List<JSONObject> readJsonObjectList(String file){
        ArrayList<JSONObject> list = new ArrayList<>();
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String s  =null;
            while((s= reader.readLine())!= null){
                try {
                    result += s;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            reader.close();
            JSONArray array = new JSONArray(result);
            for (Object anArray : array) {
                list.add((JSONObject) anArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
