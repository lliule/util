package com.lly.test.j8.date;

import com.lly.util.TimeUtil;
import org.junit.Test;

import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static java.lang.System.out;

public class LocalDateTest {

    @Test
    public void test1(){
//        LocalDate now = LocalDate.now();
//        out.println(now);
//        LocalTime time = LocalTime.now();
//        out.println(time);
//
//        LocalDateTime dateTime = LocalDateTime.now();
//        out.println(dateTime);
//
//        LocalDate parse = LocalDate.parse("2017-01-01");
//        out.println(parse);
//
//        out.println(now.getMonthValue());
//
//        out.println(""+now.getYear()+now.getMonthValue() + now.getDayOfMonth());

//        out.println(LocalDate.of(11,1,-1));


        LocalDate now = LocalDate.now();
        LocalDate tomorrow = now.plusDays(1);
        /*

        out.println(now);
        LocalDate plus = now.plus(1, ChronoUnit.YEARS);

        out.println(localDate);
        out.println(plus);*/

        /*out.println(now.isAfter(tomorrow));
        out.println(now.isLeapYear());//�Ƿ�������

        out.println(Instant.now().atZone(ZoneOffset.ofHours(8)));//��ȡ����ʱ���

        out.println(Clock.system(ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))).instant());

        out.println(LocalDate.parse("20170706", DateTimeFormatter.BASIC_ISO_DATE));
//        out.println(LocalDateTime.parse("2017/07/06 11:11:11",DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss")));

        LocalDateTime dateTime = LocalDateTime.now();
        out.println(dateTime.format(DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss")));

        out.println(dateTime.format(DateTimeFormatter.ISO_DATE));*/

//        out.println(TimeUtil.getNow());
//        out.println(TimeUtil.format(LocalDateTime.now(), "YYYYMMddHHmmss"));


    }
}
