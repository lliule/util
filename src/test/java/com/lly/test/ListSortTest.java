package com.lly.test;

import org.junit.Test;

import java.util.*;

import static java.lang.System.out;
import static java.util.Arrays.sort;

public class ListSortTest {

    @Test
    public void sortTest(){
        User[] users = new User[10];
        for(int i=0;i<5;i++){ // 5个在线主播，热度随机
            User user = new User(1, new Random().nextInt(10));
            users[i] = user;
        }
        for(int i=5;i<10;i++){//5个不在线
            User user = new User(0, new Random().nextInt(10));
            users[i] = user;
        }

        out.println(Arrays.asList(users).toString());

        long start = System.nanoTime();
        sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u2.getOnline() - u1.getOnline() > 0 ?
                        1 : ((u2.getOnline() - u1.getOnline()) == 0
                        ? (u2.getStatus() - u1.getStatus()) : -1);
            }
        });
/*
        sort(users, (u1, u2) -> {
            return (u2.getOnline() - u1.getOnline() > 0)
                    ? 1
                    : ((u2.getOnline() - u1.getOnline()) == 0 ? (u2.getStatus() - u1.getStatus()) : -1);
        });*/


       /* List<User> list = Arrays.asList(users);
        Object[] array = list.stream().sorted((u1, u2) -> {
            return (u2.getOnline() - u1.getOnline() > 0)
                    ? 1
                    : ((u2.getOnline() - u1.getOnline()) == 0 ? (u2.getStatus() - u1.getStatus()) : -1);
        }).toArray();*/

        long end =  System.nanoTime();
//        out.println(Arrays.asList(array).toString()+System.lineSeparator()+"totalTime:"+(end-start));
        out.println(Arrays.asList(users).toString()+System.lineSeparator()+"totalTime:"+(end-start));
// --113055194  123781402  106213639
//        114484589 101012481 95567480

    }

    @Test
    public void listAscTest(){
        List<User> userList = getUserList();
        System.out.println(userList);
        userList.sort((o1, o2) -> o2.getId() - o1.getId());
        System.out.println(Arrays.toString(userList.toArray()));
    }


    private List<User> getUserList(){
        User[] users = new User[10];
        for(int i=0;i<5;i++){ // 5个在线主播，热度随机
            User user = new User(i,1, new Random().nextInt(10));
            users[i] = user;
        }
        for(int i=5;i<10;i++){//5个不在线
            User user = new User(i,0, new Random().nextInt(10));
            users[i] = user;
        }
        return Arrays.asList(users);
    }

}



class User{

    public User(int online, int status) {
        this.online = online;
        this.status = status;
    }

    public User(int id, int online, int status) {
        this.id = id;
        this.online = online;
        this.status = status;
    }

    private int id;
    private int online;
    private int status;

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", online=" + online +
                ", status=" + status +
                '}';
    }
}