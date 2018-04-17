package com.lly.test.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(sex, user.sex) &&
                Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, sex, age);
    }
}
