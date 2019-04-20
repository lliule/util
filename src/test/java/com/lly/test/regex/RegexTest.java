package com.lly.test.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leliu
 *  正则
 */
public class RegexTest {

	@Test
	public void test(){

		String str = "福州三中高二物理校 本 馕;20181220\n洛伦兹力\r-\t_！!a#a$b&c;d.e?f\tg=h+i-j@k~l`m*.。；‘“':：{{[【|";
		String test = "你好";
		Matcher matcher = Pattern.compile("[a-zA-Z0-9\\u4e00-\\u9fa5]+").matcher(test);
		String a = "";
		while (matcher.find()){
			a += matcher.group();
		}
		// 福州三中高二物理校本馕20181220洛伦兹力aabcdefghijklm
		System.out.println(a);

	}


}
