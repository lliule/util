package com.lly.test.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by dana on 2018/4/1.
 *
 * @author dana
 */
@Getter
@Setter
public class User {

    private static Integer ID = 1;
    private String name;
    private Integer id ;
    private String sex;
    private Integer age;

    public User() {
    }

    public User(String name, String sex, Integer age) {
        this.name = name;
        this.id = ID++;
        this.sex = sex;
        this.age = age;
    }
}
