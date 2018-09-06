package com.lly.test.export.excel.poi;

import lombok.Getter;

import java.util.Date;

@Getter
public class Student {
    private long id;
    private String name;
    private int age;
    private Boolean sex;
    private Date birthday;

    public Student() {
    }

    public Student(long id, String name, int age, boolean sex, Date birthday) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
    }

}
