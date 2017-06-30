package com.lly.test;

import com.lly.common.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
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
}
