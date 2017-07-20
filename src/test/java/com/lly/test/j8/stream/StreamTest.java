package com.lly.test.j8.stream;

import com.lly.test.model.User;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;
public class StreamTest {


    @Test
    public void testMinOrMax() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("G:/small_sku.txt"));
//        int min = reader.lines().mapToInt(String::length).max().getAsInt();//获取行数最大的长度
//        out.println(min);
//            reader.close();
        //注意：每个流只能使用一次。如果我把上面的注释打开在执行，则下面的代码返回的是一个空数组。reader.lines已经变成一个空了
        //但是我将流关闭在重新开启则没有问题；
        List<String> list = reader.lines()//获取文件流，以行分开
                .flatMap(line -> Stream.of(line.split(" ")))//获取文件的单词，以 ‘ ’ 分号分开
                .filter(world -> world.length() > 0) // 筛选，
                .map(String::toUpperCase)//对每个单词转大写
                .distinct()//去掉重复的单词
                .sorted((a,b)-> a.length()-b.length())//按长度排序
                .collect(Collectors.toList());//生成结果集
        reader.close();//关闭流
        out.println(list.toString());
    }


    @Test
    public void testMatch(){
        ArrayList<User> list = new ArrayList<>();
        User user = new User("dana", 27);
        User user1 = new User("lily", 23);
        User user2 = new User("candice", 18);
        User user3 = new User("vic", 35);
        User user4 = new User("xm", 17);
        list.add(user);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);

        boolean allMatch = list.stream().allMatch(user5 -> user5.getAge() > 18);
        boolean anyMatch = list.stream().anyMatch(user5 -> user5.getAge() < 18);
        boolean noneMatch = list.stream().noneMatch(user5 -> user5.getAge() < 50);
        out.println("all user is adult? :" + allMatch);
        out.println("have child? ：" + anyMatch);
        out.println("have older ?:" +noneMatch);

    }


    @Test
    public void testGeneratorStream(){
        Random random = new Random();
        Supplier<Integer> rand = random::nextInt;
        Stream.generate(rand).limit(10)//必须使用limit来限制大小，否则无穷大
                .forEach(out::println);
    }

    @Test
    public void testIterator(){
        Stream.iterate(0,integer -> integer+2).limit(10)//必须限制大小。生成一个等差数组
                .forEach(out::println);

    }
}
