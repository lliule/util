package com.lly.test.jdk;

import com.lly.test.model.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
            }
        }

    }

}
