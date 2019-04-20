package com.lly.test.jdk;

import com.alibaba.fastjson.JSON;
import com.lly.test.model.User;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ForEachBreakTest {


    @Test
    public void testFor(){
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4);
        List<User> userList = Arrays.asList(new User("a",1), new User("b",3), new User("c",4),new User("d",2));
        LABEL: for (Integer integer : integerList) {
            for (User user : userList) {
                if(user.getAge() == integer){
                    System.out.println(integer);
                    continue LABEL;
                }
                System.out.println("-- " + integer);
            }
        }
    }

    /**
     * 双重for ,break只会跳出当前的for循环，和continue的范围一样
     */
    @Test
    public void  testDistinct(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<User> userList = Arrays.asList(new User("a",1), new User("b",3), new User("a",3),new User("d",2));

        ConcurrentHashMap<String, User> map = new ConcurrentHashMap<>();
        for (User user : userList) {
            map.putIfAbsent(user.getName(), user);
        }
        ArrayList<User> users = new ArrayList<>();
        users.addAll(map.values());
        System.out.println(JSON.toJSONString(users));
    }


    @Test
    public void testSet(){
        HashSet<String> strings = new HashSet<>();
        strings.add("a");
        System.out.println(strings.add("a"));
        System.out.println(strings.add("b"));

        ArrayList<String> list = new ArrayList(){{add("1");add("2");add(null); add("4");}};
        list.removeAll(Collections.singleton(null));
        Iterator<String> iterator = list.iterator();

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            list.remove(s);
            i = i -1;
            continue;
        }

        System.out.println(list);

    }

}
