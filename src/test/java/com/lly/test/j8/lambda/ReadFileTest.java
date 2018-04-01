package com.lly.test.j8.lambda;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import static java.lang.System.out;

/**
 * 自定义lambda实现
 */
public class ReadFileTest {

    public String read(BufferPrcess br,String file) throws Exception {
//        try(BufferedReader br1 = new BufferedReader(new FileReader(file))){//"G:/new.json"
//                return br.process(br1);
//        }
        BufferedReader br1 = new BufferedReader(new FileReader(file));
        String process = br.process(br1);
        br1.close();
        return process;
    }
    String  file = "G:/new.json";

    @Test
    public void testReadFile() throws Exception {
        String read = read(BufferedReader::readLine,"G:/new.json");

        String s = read(b -> b.readLine() + b.readLine(),file);

        String s2 = read(b -> {
            String s1, result = "";
            while ((s1 = b.readLine()) != null) {
                result += s1;
            }
            return result;
        }, file);
        out.println(read);
        out.println(s);
        out.println(s2);
    }
}
