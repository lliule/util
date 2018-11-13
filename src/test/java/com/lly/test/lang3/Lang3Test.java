package com.lly.test.lang3;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * apache lang3 工具类测试
 */
public class Lang3Test {

    @Test
    public void testisBlank(){
        /**
         * isBlank是对字符进行判断，只要有不为 空格的就范围false
         * 否则返回true
         * 建议： 在调用IsBlank之前先trim
         */
        String test = "      1  ";
        boolean blank = false;
        System.out.println(System.currentTimeMillis());

        for (int i = 0; i < 1000; i++) {
            blank = StringUtils.isBlank(test);
        }
        System.out.println(blank);
        System.out.println(System.currentTimeMillis());
       /*
        for (int j = 0; j < 1000; j++) {
            String trim = test.trim();
            blank = StringUtils.isBlank(trim);
        }

        System.out.println(blank);
        System.out.println(System.currentTimeMillis());*/
    }


    @Test
    public void testIsBlankBetter(){
        boolean blank = false;
        String test = "      1  ";
        System.out.println(System.currentTimeMillis());
        for (int i = 0; i < 1000; i++) {
            String trim = test.trim();
            blank = StringUtils.isBlank(trim);
        }
        System.out.println(blank);
        System.out.println(System.currentTimeMillis());
    }

    /**
     * isEmpty
     *校验是否为空或者长度为 0
     */
    @Test
    public void testIsEmpty(){
        String test = "    ";
        System.out.println(StringUtils.isEmpty(test));
        Boolean flag  = null;
        System.out.println(BooleanUtils.isTrue(flag));
    }


}

