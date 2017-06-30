package com.lly.test;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.lang.System.out;

public class LocalDateTest {

    @Test
    public void test1(){
        LocalDate now = LocalDate.now();
        out.println(now);
        LocalTime time = LocalTime.now();
        out.println(time);

        LocalDateTime dateTime = LocalDateTime.now();
        out.println(dateTime);

        LocalDate parse = LocalDate.parse("2017-01-01");
        out.println(parse);

        out.println(now.getMonthValue());

        out.println(""+now.getYear()+now.getMonthValue() + now.getDayOfMonth());
    }
}
