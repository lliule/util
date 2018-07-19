package com.lly.test;

import com.lly.common.LogUtil;
import com.lly.read.Read2String;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.out;
public class UtilTest {

    @Test
    public void test1(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
//        final String  s  ="";
//        list.stream().map((n,s) -> {s += n+",";});
        out.println(StringUtils.join(list,','));
    }

    @Test
    public void test2() throws IOException {
        LogUtil.log("11 \n");
    }


    @Test
    public void test3(){

        ArrayList<String> strings = new ArrayList<>(100);
        if(strings.size()<1){
            out.println(1);
        }else{
            out.println(2);
        }
        strings.add("1");
        if(strings.size()<1){
            out.println(1);
        }else{
            out.println(2);
        }
        String remove = strings.remove(0);
        out.println(remove);
        if(strings.size()<1){
            out.println(1);
        }else{
            out.println(2);
        }
//        strings.get()
    }

    //将string带小数点的字符串转换成整数
    @Test
    public void test4(){
        String a = "1111.0";
        BigDecimal decimal = new BigDecimal(a);
        long value = decimal.longValue();
        out.println(value);
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
