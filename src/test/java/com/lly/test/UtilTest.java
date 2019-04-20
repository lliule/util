package com.lly.test;

import com.lly.read.Read2String;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class UtilTest {

    @Test
    public void test1(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(3);
        list2.add(4);

        list.removeAll(list2);


        out.println(StringUtils.join(list,','));
        out.println(StringUtils.join(list2,','));
//        list.clear();
//        System.out.println(StringUtils.join(list,','));
    }

    @Test
    public void test2() {
        Pattern pattern = Pattern.compile("\\++");
        String sign = "1+2+3";
        try {
            Matcher matcher = pattern.matcher(sign);
            System.out.println(matcher.find());
            String replaceAll = matcher.replaceAll("%2B");
            String format = String.format("aaa %s", replaceAll);
//            System.out.println(URLEncoder.encode(sign,"UTF-8"));
            System.out.println(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test3(){

        HashMap<String, String> map = new HashMap<>(10);
        map.put("a","1");
        for (String s : map.keySet()) {
            System.out.println(s);
        }
    }

    //将string带小数点的字符串转换成整数
    @Test
    public void test4() throws UnsupportedEncodingException {
        String urlPath = "http://zhixuetest.oss-cn-hangzhou.aliyuncs.com/hanzhang_test/tfb/word/2018/12/04/南京高二年级英语组卷智提分试卷(B5)_28e7a925-e7d3-46f8-be31-10fb5b55e5db.docx";
        String host = urlPath.substring(0,urlPath.lastIndexOf('/')+1);
        String name = urlPath.substring(urlPath.lastIndexOf('/'),urlPath.lastIndexOf('.'));
        String suffix = urlPath.substring(urlPath.lastIndexOf('.'),urlPath.length());
        String encodeName = URLEncoder.encode(name, "UTF-8");
        urlPath = host + encodeName + suffix;
        System.out.println(urlPath);
    }

    /**
     * 读取一个文件，然后将读取的结果 根据行作为  带\""\字符串数组 重新写回去
     * @throws FileNotFoundException
     */
    @Test
    public void testReadFileToArray() throws FileNotFoundException {
        String file = "C:\\Users\\Administrator\\Desktop\\备份\\11.txt";
        String[] strings = Read2String.readFileToArray(file);
        out.println(Arrays.asList(strings));
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(Arrays.asList(strings).toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
